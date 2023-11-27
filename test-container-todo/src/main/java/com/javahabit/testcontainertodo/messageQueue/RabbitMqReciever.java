package com.javahabit.testcontainertodo.messageQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqReciever {

    private static  final Logger LOGGER = LoggerFactory.getLogger(RabbitMqReciever.class);
    //@RabbitListener(queues = {"${rabbitMq.queue.name}"})
    public void consumeMessage(String message){
        LOGGER.info("Got messaage: " + message);
    }
}
