package com.andrii.user.controller;

import com.andrii.amqp.RabbitMQMessageProducer;
import com.andrii.email.EmailRequest;
import com.andrii.user.aop.RequiresUserAspect;
import com.andrii.user.entity.User;
import com.andrii.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @GetMapping("/generateGreetingEmail")
    @RequiresUserAspect.RequiresUser
    public void greeting(Long id){
        User user = userService.findById(id);
        rabbitMQMessageProducer.publish(new EmailRequest(id, user.getFirstName(), user.getEmail()),
                "internal.exchange",
                "internal.smtp.routing-key");
    }

    //TODO: add all business logic with adding friends
}
