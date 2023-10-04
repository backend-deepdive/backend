package com.worker.worker.socket.bithumb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.worker.producer.KafkaProducer;
import com.worker.worker.socket.bithumb.request.BithumbRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class BithumbWsListener extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final KafkaProducer producer;
    @Value("${spring.kafka.topic.bithumb}")
    String topicName;

    @Autowired
    public BithumbWsListener(ObjectMapper objectMapper, KafkaProducer producer) {
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
        /*
        * request 스팩 정의
        * type: ticker, transaction, orderbookdepth
        * symbols: List.of([COIN]_KRW)
        * tickTypes: ticker 일때만 필요한 파라미터
        * */
        BithumbRequest request =
                BithumbRequest.builder()
                        .type("ticker")
                        .symbols(List.of("BTC_KRW" , "ETH_KRW"))
                        .tickTypes(List.of("1H"))
                        .build();
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws JsonProcessingException {
        String payload = textMessage.getPayload();
        if(!payload.contains("status")) {
            JsonNode jsonNode = objectMapper.readTree(textMessage.getPayload());
            HashMap<String, Object> message = objectMapper.convertValue(jsonNode, HashMap.class);

            JsonNode contentNode = jsonNode.get("content");
            HashMap<String, Object> content = objectMapper.convertValue(contentNode, HashMap.class);

            producer.sendMessage(topicName, content);
        }
    }
}