package com.api.client.upbit.service;

import com.core.Exchange;
import com.domain.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UpbitService {
    private final MarketService marketService;
    public void save(Exchange exchange, List<String> codes) {
        marketService.save(exchange, codes);
    }
}

