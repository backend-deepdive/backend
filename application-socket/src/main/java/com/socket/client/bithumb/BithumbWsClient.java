package com.socket.client.bithumb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class BithumbWsClient {
    private final BithumbWsListener bithumbWsListener;
    private final String socketPath = "wss://pubwss.bithumb.com/pub/ws";

    @Autowired
    public BithumbWsClient(BithumbWsListener bithumbWsListener) {
        this.bithumbWsListener = bithumbWsListener;
//        connect();
    }

    public void connect() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            client.setTaskExecutor(client.getTaskExecutor());
            WebSocketConnectionManager manager = new WebSocketConnectionManager(
                    client,
                    bithumbWsListener,
                    socketPath
            );
            manager.setAutoStartup(true);
            manager.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
