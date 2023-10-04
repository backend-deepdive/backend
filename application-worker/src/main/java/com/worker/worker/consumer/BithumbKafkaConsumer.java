package com.worker.worker.consumer;

import com.core.Exchange;
import com.domain.service.TickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BithumbKafkaConsumer {
//    private final ProxyJpaInterface proxyJpaInterface;
    private final TickerService tickerService;

    @KafkaListener(topics = "${spring.kafka.topic.bithumb}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HashMap<String, Object> data){
        // ticker라고 가정하고 처리(ticker, trade, overbook 관련된 interface 처리가 필요함)

        tickerService.save(Exchange.BITHUMB, data);
    }
}
