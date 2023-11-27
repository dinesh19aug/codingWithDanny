package com.javahabit.testcontainertodo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitMq.exchange.name}")
    private String rmqTopicExgngName;
    @Value("${rabbitMq.queue.name}")
    private String rmqQueueName ;

    @Value("${rabbitMq.routingKey.name}")
    private String routingKey;

    @Bean
    Queue queue(){
        //name – the name of the queue. durable – true if we are declaring a durable queue (the queue will survive a server restart)
        return new Queue(rmqQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rmqTopicExgngName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

}
