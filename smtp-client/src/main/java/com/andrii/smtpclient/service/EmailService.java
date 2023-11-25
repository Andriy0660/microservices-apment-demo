package com.andrii.smtpclient.service;

import com.andrii.email.EmailRequest;
import com.andrii.email.SuccessRegistrationInfo;
import com.andrii.smtpclient.email.EmailBuilder;
import com.andrii.smtpclient.email.EmailSender;
import com.andrii.smtpclient.email.SendingEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSender emailSender;
    public void sendGreeting(EmailRequest emailRequest){
        String message = EmailBuilder.buildMessage(emailRequest);
        emailSender.send(new SendingEmailRequest(message, emailRequest.getEmail(), "Greeting"));
    }
    public void sendSuccessRegistrationInfo(SuccessRegistrationInfo info){
        String message = EmailBuilder.buildMessage(info);
        emailSender.send(new SendingEmailRequest(message, info.getEmail(), "Success Registration!"));
    }
}
