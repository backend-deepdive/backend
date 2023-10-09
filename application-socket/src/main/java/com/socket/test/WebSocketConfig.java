package com.socket.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.worker.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaProducer kafkaProducer;

    @Bean
    public TestWebSocketServer testWebSocketServer() {
        return new TestWebSocketServer(objectMapper, kafkaProducer);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(testWebSocketServer(), "/wwss");
    }
}

