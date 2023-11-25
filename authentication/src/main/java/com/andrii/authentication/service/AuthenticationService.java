package com.andrii.authentication.service;

import com.andrii.amqp.RabbitMQMessageProducer;
import com.andrii.authentication.dto.request.AuthenticationRequest;
import com.andrii.authentication.dto.request.RegisterRequest;
import com.andrii.authentication.dto.response.AuthenticationResponse;
import com.andrii.authentication.entity.User;
import com.andrii.authentication.exception.BadRequestException;
import com.andrii.authentication.exception.UnauthorizedException;
import com.andrii.email.SuccessRegistrationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;
    private final UserService userService;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void signUp(RegisterRequest request)
    {
        String email = request.getEmail();

        if(userService.existsByEmail(email)){
            throw new BadRequestException("The email is already used");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        userService.save(user);
        rabbitMQMessageProducer.publish(new SuccessRegistrationInfo(request.getEmail()),
                "internal.exchange",
                "internal.smtp.routing-key");
    }

    public AuthenticationResponse signIn(AuthenticationRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        if(!userService.existsByEmail(request.getEmail())) {
            throw new UnauthorizedException("There is no user with this email. Please sign up");
        }

        User user = userService.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Invalid password!");
        }

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
