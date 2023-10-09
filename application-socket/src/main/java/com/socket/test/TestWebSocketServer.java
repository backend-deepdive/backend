package com.socket.test;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.worker.producer.KafkaProducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import javax.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@ServerEndpoint("/wwss")
public class TestWebSocketServer extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final KafkaProducer producer;
    @Value("${spring.kafka.topic.bithumb}")
    String topicName;

    @Autowired
    public TestWebSocketServer(ObjectMapper objectMapper, KafkaProducer producer) {
        this.objectMapper = objectMapper;
        this.producer = producer;
    }

//    public TestWebSocketServer() {
//        this.objectMapper = null; // 혹은 null 대신에 초기화 코드 추가
//        this.producer = null; // 혹은 null 대신에 초기화 코드 추가
//    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket 연결이 열렸습니다.");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        System.out.println("받은 메시지: " + message.getPayload());
        try {
            session.sendMessage(new TextMessage("서버에서 보낸 응답: " + message.getPayload()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws JsonProcessingException {
        String payload = textMessage.getPayload();
        if(!payload.contains("status")) {
            JsonNode jsonNode = objectMapper.readTree(textMessage.getPayload());
            HashMap<String, Object> message = objectMapper.convertValue(jsonNode, HashMap.class);

            /*
             * response 로직 정리
             * type: ticker, transaction, orderbookdepth
             * */
            String type = jsonNode.get("type").asText();
            HashMap<String, Object> content = null;

            switch (type) {
                case "ticker":
                    JsonNode contentNode = jsonNode.get("content");
                    content = objectMapper.convertValue(contentNode, HashMap.class);
                    content.put("type", jsonNode.get("type").asText());
                    producer.sendMessage(topicName, content);
                    break;

                case "transaction":
                    JsonNode contentNode2 = jsonNode.get("content");
                    if (contentNode2 != null) {
                        JsonNode listNode = contentNode2.get("list");
                        if (listNode != null && listNode.isArray()) {
                            for(JsonNode itemNode : listNode) {
                                content = objectMapper.convertValue(itemNode, HashMap.class);
                                content.put("type", jsonNode.get("type").asText());
                                producer.sendMessage(topicName, content);
                            }
                        }
                    }
                    break;

                case "orderbookdepth":
                    break;
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
        throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeLatch.countDown();
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
