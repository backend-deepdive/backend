package com.worker.worker.producer;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, HashMap<String, Object>> kafkaTemplate;

    public void sendMessage(String topicName, HashMap<String, Object> data){
        Message<HashMap<String, Object>> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
//
//        System.out.println("@@@@@@@@@@@@@@");
//        System.out.println(message);
//        System.out.println("@@@@@@@@@@@@@@");
        kafkaTemplate.send(message);
    }
}