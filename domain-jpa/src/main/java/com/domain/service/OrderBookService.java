package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Orderbook;
import com.domain.mapper.OrderBookMapper;
import com.domain.repository.OrderBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OrderBookService {
    private final OrderBookRepository orderBookRepository;
    private final OrderBookMapper orderBookMapper;

    public void save(Exchange exchange, HashMap<String, Object> data) {
        Orderbook orderbook = orderBookMapper.toEntity(exchange, data);
        orderBookRepository.save(orderbook);
    }
}
