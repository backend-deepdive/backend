package com.socket.test;

import com.domain.global.CoinManagerIF;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ObjectMapper objectMapper;
    private final CoinManagerIF coinManagerIF;

//    @Bean
//    public TestWebSocketServer testWebSocketServer() {
//        return new TestWebSocketServer(objectMapper, kafkaProducer);
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TestWebSocketServer(objectMapper,coinManagerIF), "/wwss");
    }
}

