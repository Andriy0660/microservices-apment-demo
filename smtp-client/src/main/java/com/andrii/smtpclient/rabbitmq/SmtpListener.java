package com.andrii.smtpclient.rabbitmq;

import com.andrii.email.EmailRequest;
import com.andrii.email.SuccessRegistrationInfo;
import com.andrii.smtpclient.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SmtpListener {
    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.smtp}")
    public void proceed(EmailRequest emailRequest) {
        log.info("Consumed {} from queue", emailRequest);
        emailService.sendGreeting(emailRequest);
    }
    @RabbitListener(queues = "${rabbitmq.queues.smtp}")
    public void proceed(SuccessRegistrationInfo info) {
        log.info("Consumed {} from queue", info);
        emailService.sendSuccessRegistrationInfo(info);
    }
}
