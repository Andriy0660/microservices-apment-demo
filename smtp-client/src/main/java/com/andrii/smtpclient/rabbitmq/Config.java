package com.andrii.smtpclient.rabbitmq;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class Config {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.smtp}")
    private String smtpQueue;

    @Value("${rabbitmq.routing-keys.internal-smtp}")
    private String internalSmtpRoutingKey;
    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }
    @Bean
    public Queue smtpQueue() {
        return new Queue(this.smtpQueue);
    }

    @Bean
    public Binding internalToSmtpBinding() {
        return BindingBuilder
                .bind(smtpQueue())
                .to(internalTopicExchange())
                .with(this.internalSmtpRoutingKey);
    }
}
