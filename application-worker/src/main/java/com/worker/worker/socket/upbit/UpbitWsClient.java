package com.worker.worker.socket.upbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class UpbitWsClient {
    private final UpbitWsListener upbitWsListener;
    private final String socketPath = "wss://api.upbit.com/websocket/v1";

    @Autowired
    public UpbitWsClient(UpbitWsListener upbitWsListener) {
        this.upbitWsListener = upbitWsListener;
//        connect();
    }

    public void connect() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            client.setTaskExecutor(client.getTaskExecutor());
            WebSocketConnectionManager manager = new WebSocketConnectionManager(
                    client,
                    upbitWsListener,
                    socketPath
            );
            manager.setAutoStartup(true);
            manager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
