package com.worker.worker.socket.upbit;

import com.fasterxml.jackson.databind.JsonNode;
import com.worker.global.util.json.JsonUtil;
import com.worker.worker.producer.KafkaProducer;
import com.worker.worker.socket.upbit.request.UpbitCodeRequest;
import com.worker.worker.socket.upbit.request.UpbitTicketRequest;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class UpbitWsListener extends WebSocketListener {
    private final KafkaProducer producer;
    @Value("${spring.kafka.topic.upbit}")
    String topicName;

    @Autowired
    public UpbitWsListener(KafkaProducer producer) {
        this.producer = producer;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closed : %s / %s\r\n", code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closing : %s / %s\n", code, reason);
        webSocket.close(1000, null);
        webSocket.cancel();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        System.out.println("Socket Error : " + t.getMessage());
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        JsonNode jsonNode = JsonUtil.fromJson(text, JsonNode.class);
    }

    /*
    * bithumb과 동일한 코드를 사용할 수 없는 이유는 socket으로 부터 수신되는 데이터가 bithumb은 String type,
    * Upbit은 ByteString type으로 들어오는데 타입간의 차이때문에 socket에 직접적으로 접근하는 okHttp 클래스를 사용함.
    * */
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        JsonNode jsonNode = JsonUtil.fromJson(bytes.string(StandardCharsets.UTF_8), JsonNode.class);
        HashMap<String, Object> message = JsonUtil.toJson(jsonNode.toString(), HashMap.class);
        producer.sendMessage(topicName, message);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        UpbitTicketRequest ticket =
                UpbitTicketRequest.builder()
                        .ticket(UUID.randomUUID().toString())
                        .build();

        UpbitCodeRequest type =
                UpbitCodeRequest.builder()
                        .type("ticker")
                        .codes(List.of("KRW-BTC"))
                        .build();
        webSocket.send(JsonUtil.toJson(List.of(ticket, type)));
    }
}

