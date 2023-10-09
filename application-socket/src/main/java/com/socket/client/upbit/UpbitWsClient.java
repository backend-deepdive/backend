//package com.socket.client.upbit;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UpbitWsClient {
//    private final UpbitWsListener upbitWsListener;
////    private final String socketPath = "wss://api.upbit.com/websocket/v1";
//    private final String socketPath = "";
//
//    @Autowired
//    public UpbitWsClient(UpbitWsListener upbitWsListener) {
//        this.upbitWsListener = upbitWsListener;
//        connect();
//    }
//
//    public void connect() {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(socketPath)
//                .build();
//        try {
//            client.newWebSocket(request, upbitWsListener);
//
//            client.dispatcher()
//                  .executorService()
//                  .shutdown();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
