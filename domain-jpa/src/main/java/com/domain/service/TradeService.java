package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Trades;
import com.domain.mapper.TradeMapper;
import com.domain.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TradeService{
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public void save(Exchange exchange, HashMap<String, Object> data) {
        Trades trade = tradeMapper.toEntity(exchange, data);
        tradeRepository.save(trade);
    }
}
