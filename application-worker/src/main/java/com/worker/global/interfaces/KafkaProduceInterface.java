package com.worker.global.interfaces;

import java.util.HashMap;

public interface KafkaProduceInterface {
    public void sendMessage(HashMap<String, Object> data);
}
