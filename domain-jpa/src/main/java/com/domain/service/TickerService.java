package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Ticker;
import com.domain.mapper.TickerMapper;
import com.domain.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TickerService {
    private final TickerRepository tickerRepository;
    private final TickerMapper tickerMapper;

    public void save(Exchange exchange, HashMap<String, Object> data) {
        Ticker ticker = tickerMapper.toEntity(exchange, data);
        tickerRepository.save(ticker);
    }
}
