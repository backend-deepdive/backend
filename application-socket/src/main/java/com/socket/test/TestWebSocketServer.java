package com.socket.test;



import com.core.Exchange;
import com.domain.global.CoinManagerIF;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import javax.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.*;

@Component
@ServerEndpoint("/wwss")
@RequiredArgsConstructor
public class TestWebSocketServer implements WebSocketHandler {
    private final ObjectMapper objectMapper;
    private CountDownLatch closeLatch = new CountDownLatch(1);
//    private final KafkaProducer producer;
    private final CoinManagerIF coinManagerIF;

//    @Value("${spring.kafka.topic.bithumb}")
    String topicName = "topic-bithumb";
//    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

//    @Autowired
//    public TestWebSocketServer(ObjectMapper objectMapper, KafkaProducer producer) {
//        this.objectMapper = objectMapper;
//        this.producer = producer;
//    }

//    public TestWebSocketServer() {
//        this.objectMapper = null; // 혹은 null 대신에 초기화 코드 추가
//        this.producer = null; // 혹은 null 대신에 초기화 코드 추가
//    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket 연결이 열렸습니다.");
//        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> textmessage) throws JsonProcessingException {
        System.out.println("받은 메시지: " + textmessage.getPayload());
//        JsonNode testjsonNode = objectMapper.readTree((String) textmessage.getPayload());
//        System.out.println(testjsonNode);
        String payload = (String) textmessage.getPayload();
        if(!payload.contains("status")) {
            JsonNode jsonNode = objectMapper.readTree((String) textmessage.getPayload());
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
//                    producer.sendMessage(topicName, content);
                    break;

                case "transaction":
                    JsonNode contentNode2 = jsonNode.get("content");
                    if (contentNode2 != null) {
                        JsonNode listNode = contentNode2.get("list");
                        if (listNode != null && listNode.isArray()) {
                            for(JsonNode itemNode : listNode) {
                                content = objectMapper.convertValue(itemNode, HashMap.class);
                                content.put("type", jsonNode.get("type").asText());
//                                System.out.println("@@@@@@@@@@@@@@");
//                                System.out.println(content);
//                                System.out.println("@@@@@@@@@@@@@@");
//                                producer.sendMessage(topicName, content);
                                try{
                                    coinManagerIF.save(Exchange.BITHUMB,content);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    break;

                case "orderbookdepth":
                    break;
            }
        }
    }
//    @Override
//    public void handleMessage(WebSocketSession session, TextMessage textMessage) throws JsonProcessingException {
//        String payload = textMessage.getPayload();
//        if(!payload.contains("status")) {
//            JsonNode jsonNode = objectMapper.readTree(textMessage.getPayload());
//            HashMap<String, Object> message = objectMapper.convertValue(jsonNode, HashMap.class);
//
//            /*
//             * response 로직 정리
//             * type: ticker, transaction, orderbookdepth
//             * */
//            String type = jsonNode.get("type").asText();
//            HashMap<String, Object> content = null;
//
//            switch (type) {
//                case "ticker":
//                    JsonNode contentNode = jsonNode.get("content");
//                    content = objectMapper.convertValue(contentNode, HashMap.class);
//                    content.put("type", jsonNode.get("type").asText());
//                    producer.sendMessage(topicName, content);
//                    break;
//
//                case "transaction":
//                    JsonNode contentNode2 = jsonNode.get("content");
//                    if (contentNode2 != null) {
//                        JsonNode listNode = contentNode2.get("list");
//                        if (listNode != null && listNode.isArray()) {
//                            for(JsonNode itemNode : listNode) {
//                                content = objectMapper.convertValue(itemNode, HashMap.class);
//                                content.put("type", jsonNode.get("type").asText());
//                                producer.sendMessage(topicName, content);
//                            }
//                        }
//                    }
//                    break;
//
//                case "orderbookdepth":
//                    break;
//            }
//        }
//    }

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
