package com.worker.worker.socket.upbit;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.worker.producer.KafkaProducer;
import com.worker.worker.socket.upbit.request.UpbitCodeRequest;
import com.worker.worker.socket.upbit.request.UpbitTicketRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class UpbitWsListener extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final KafkaProducer producer;
    @Value("${spring.kafka.topic.upbit}")
    String topicName;

    @Autowired
    public UpbitWsListener(ObjectMapper objectMapper, KafkaProducer producer) {
        this.objectMapper = objectMapper;
        this.producer = producer;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeLatch.countDown();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        UpbitTicketRequest ticket =
                UpbitTicketRequest.builder()
                        .ticket(UUID.randomUUID().toString())
                        .build();

        UpbitCodeRequest code =
                UpbitCodeRequest.builder()
                        .type("ticker")
                        .codes(List.of())
                        .build();
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(List.of(ticket, code))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws JsonProcessingException {
        String ss = textMessage.getPayload();
        if(ss.contains("status")) {
            JsonNode jsonNode = objectMapper.readTree(textMessage.getPayload());
            HashMap<String, Object> message = objectMapper.convertValue(jsonNode, HashMap.class);
            log.info(String.valueOf(message));
//        producer.sendMessage(topicName, message);
        }

    }
}
