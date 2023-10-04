package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Trades;
import com.domain.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TradeService{
    private final TradeRepository tradeRepository;

    public void save(Exchange exchange, HashMap<String, Object> data) {
        tradeRepository.save(Trades.toEntity(exchange, data));
    }
}
