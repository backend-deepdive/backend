package com.domain.global;

import com.core.Exchange;

import java.util.HashMap;

public interface ProxyJpaInterface {
    void save(Exchange exchange, HashMap<String, Object> data);
}
