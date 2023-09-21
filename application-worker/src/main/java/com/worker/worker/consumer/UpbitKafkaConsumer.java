package com.worker.worker.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UpbitKafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.topic.upbit}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HashMap<String, Object> data){
        System.out.println(data);
        // domain save 하는 로직 추가해야됨
    }
}
