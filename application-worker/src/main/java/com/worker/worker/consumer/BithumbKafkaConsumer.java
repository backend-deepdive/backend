package com.worker.worker.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BithumbKafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.topic.bithumb}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HashMap<String, Object> data){
        System.out.println(data);
        // domain save 하는 로직 추가해야됨
    }
}
