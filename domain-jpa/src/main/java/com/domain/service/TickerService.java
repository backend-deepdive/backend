package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Ticker;
import com.domain.global.ProxyJpaInterface;
import com.domain.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TickerService implements ProxyJpaInterface {
    private final TickerRepository tickerRepository;

    public void save(Exchange exchange, HashMap<String, Object> data) {
        tickerRepository.save(Ticker.toEntity(exchange, data));
    }
}
