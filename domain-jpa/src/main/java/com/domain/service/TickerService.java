package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Ticker;
import com.domain.global.ProxyJpaInterface;
import com.domain.repository.TickerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TickerService implements ProxyJpaInterface {
    private final TickerRepository tickerRepository;

//    public void save(Exchange exchange, HashMap<String, Object> data) {
//        tickerRepository.save(Ticker.toEntity(exchange, data));
//    }
public void save(Exchange exchange, HashMap<String, Object> data) {
    HashMap<String, Object> contentData = (HashMap<String, Object>) data.get("content");
    List<HashMap<String, Object>> contentList = (List<HashMap<String, Object>>) contentData.get("list");
    for (HashMap<String, Object> content : contentList) {
        tickerRepository.save(Ticker.toEntity(exchange, content));
    }
}
}
