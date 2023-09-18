package com.api.coin.upbit.socket;

import com.api.global.util.enums.SiseType;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UpbitWsClient {
    private final OkHttpClient client;
    private WebSocket webSocket;

    public UpbitWsClient() {
        this.client = createHttpClientWithConnectionPool();
    }

    public void connect() {
        if (webSocket == null) {
            Request request = new Request.Builder()
                    .url("wss://api.upbit.com/websocket/v1")
                    .build();

            UpbitWebSocketListener webSocketListener = new UpbitWebSocketListener();
            webSocketListener.setParameter(SiseType.TRADE, List.of("KRW-BTC"));
            webSocket = client.newWebSocket(request, webSocketListener);

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            executor.scheduleAtFixedRate(() -> {
                System.out.println("스케줄러가 수행되었습니다.(5초마다 반복)");
            }, 0, 5, TimeUnit.SECONDS);

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
