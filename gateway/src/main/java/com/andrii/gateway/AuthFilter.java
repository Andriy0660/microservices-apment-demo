package com.andrii.gateway;

import com.andrii.gateway.imports.JwtService;
import com.andrii.gateway.imports.User;
import com.andrii.gateway.imports.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(exchange.getRequest())) {

                String authHeader = null;
                final List<String> authHeaders = request.getHeaders().getOrEmpty("Authorization");
                if(authHeaders.size() > 0){
                    authHeader = authHeaders.get(0);
                }

                final String jwt;
                final String userEmail;
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return filterRequest(exchange, "You have to provide valid bearer token", HttpStatus.UNAUTHORIZED);
                }

                jwt = authHeader.substring(7);
                try {
                    //TODO: import jwt service
                    userEmail = jwtService.extractUsername(jwt);
                } catch (JwtException e) {
                    return filterRequest(exchange, "Server error", HttpStatus.INTERNAL_SERVER_ERROR);
                }

                //TODO: import user service

                Optional<User> userOpt = userService.findByEmail(userEmail);
                if(userOpt.isEmpty()){
                    return filterRequest(exchange, "Server error", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                User user = userOpt.get();
                if (!jwtService.isTokenValid(jwt,user)) {
                    return filterRequest(exchange, "Invalid credentials", HttpStatus.UNAUTHORIZED);
                }
                request.mutate().header("email",user.getEmail());
                request.mutate().header("id",user.getId().toString());
            }
            return chain.filter(exchange);
        });
    }

    public Mono<Void> filterRequest(ServerWebExchange exchange, String message, HttpStatus status){
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        exchange.getResponse().setStatusCode(status);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("httpstatus", status);
        responseBody.put("message", message);
        responseBody.put("httpcode", status.value());

        String responseBodyJson;
        try {
            responseBodyJson = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return exchange.getResponse()
                .writeWith(
                        Mono.just(exchange.getResponse().bufferFactory()
                                .wrap(responseBodyJson.getBytes()))
                );
    }
    public static class Config {

    }
}