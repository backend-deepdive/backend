package com.api.socket.upbit;

import com.api.socket.util.enums.SiseType;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UpbitWsClient {

    private final OkHttpClient client;
    private WebSocket webSocket;  // Added to hold the WebSocket instance

    public UpbitWsClient() {
        this.client = createHttpClientWithConnectionPool();
    }

    public void connect() {
        if (webSocket == null) {  // Connect only if WebSocket is not already established
            Request request = new Request.Builder()
                    .url("wss://api.upbit.com/websocket/v1")
                    .build();

            UpbitWebSocketListener webSocketListener = new UpbitWebSocketListener();
            webSocketListener.setParameter(SiseType.TICKER, List.of("KRW-DOGE"));
            webSocket = client.newWebSocket(request, webSocketListener);

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            // Schedule a task to process received data every 5 seconds
            executor.scheduleAtFixedRate(() -> {
                // You can implement your data processing logic here
                System.out.println("스케줄러가 수행되었습니다.(5초마다 반복)");
            }, 0, 5, TimeUnit.SECONDS);

            // Add shutdown hook to gracefully shutdown the executor
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                executor.shutdown();
                try {
                    executor.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    private OkHttpClient createHttpClientWithConnectionPool() {
        ConnectionPool connectionPool = new ConnectionPool(5, 5, TimeUnit.MINUTES);
        return new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .build();
    }
}
