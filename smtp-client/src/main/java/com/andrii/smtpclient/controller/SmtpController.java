package com.andrii.smtpclient.controller;

import com.andrii.email.EmailRequest;
import com.andrii.smtpclient.aop.RequiresUserAspect;
import com.andrii.smtpclient.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class SmtpController {
    private final EmailService emailService;
    @GetMapping
    @RequiresUserAspect.RequiresUser
    public void generateGreeting(Long id,
                                 @RequestParam(name = "firstName") String firstName,
                                 @RequestParam(name = "email") String email){
        emailService.sendGreeting(new EmailRequest(id,firstName,email));
    }
}
