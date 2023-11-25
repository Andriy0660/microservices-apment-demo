package com.andrii.smtpclient.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@AllArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;
    public void send(SendingEmailRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(request.getMessage(), true);
            helper.setTo(request.getEmail());
            helper.setSubject(request.getSubject());
            helper.setFrom("sandriy666@gmail.com","Microservices-project");
            mailSender.send(mimeMessage);
        }catch (UnsupportedEncodingException | MessagingException e){
            e.printStackTrace();
        }
    }
}
