package com.chryzsean.springboot_kafka_tutorial.kafka;

import com.chryzsean.springboot_kafka_tutorial.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "myGroup")
    public void consume(String message){

        LOGGER.info(String.format("Message received -> %s", message));


    }

}
