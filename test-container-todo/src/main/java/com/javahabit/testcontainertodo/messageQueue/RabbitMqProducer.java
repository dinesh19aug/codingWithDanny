package com.javahabit.testcontainertodo.messageQueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducer {
    @Value("${rabbitMq.exchange.name}")
    private String exchange;

    @Value("${rabbitMq.routingKey.name}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
