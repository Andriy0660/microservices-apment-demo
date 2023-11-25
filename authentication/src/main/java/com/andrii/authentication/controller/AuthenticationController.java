package com.andrii.authentication.controller;

import com.andrii.amqp.RabbitMQMessageProducer;
import com.andrii.authentication.dto.request.AuthenticationRequest;
import com.andrii.authentication.dto.request.RegisterRequest;
import com.andrii.authentication.dto.response.AuthenticationResponse;
import com.andrii.authentication.service.AuthenticationService;
import com.andrii.email.SuccessRegistrationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody RegisterRequest request
    ){
        authService.signUp(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.signIn(request));
    }
}