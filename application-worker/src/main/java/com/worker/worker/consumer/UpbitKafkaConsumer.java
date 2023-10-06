package com.worker.worker.consumer;

import com.core.Exchange;
import com.domain.global.CoinManagerIF;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UpbitKafkaConsumer {
    private final CoinManagerIF coinManagerInterface;
    @KafkaListener(topics = "${spring.kafka.topic.upbit}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HashMap<String, Object> data){
        coinManagerInterface.save(Exchange.UPBIT, data);
    }
}
