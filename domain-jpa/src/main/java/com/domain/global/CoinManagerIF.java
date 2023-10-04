package com.domain.global;

import com.core.Exchange;

import java.util.HashMap;

public interface CoinManagerIF {
    void save(Exchange exchange, HashMap<String, Object> data);
}
