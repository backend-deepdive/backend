package com.worker.worker.consumer;

import com.core.Exchange;
import com.domain.global.CoinManagerIF;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BithumbKafkaConsumer {
    private final CoinManagerIF coinManagerInterface;
    @KafkaListener(topics = "${spring.kafka.topic.bithumb}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HashMap<String, Object> data){
        System.out.println("@@@@@@@@@@@@@@");
        System.out.println(data);
        System.out.println("@@@@@@@@@@@@@@");
        coinManagerInterface.save(Exchange.BITHUMB, data);
    }
}
