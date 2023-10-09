package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Market;
import com.domain.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    public void save(Exchange exchange, List<String> codes) {
        codes.forEach(code ->
            marketRepository.save(Market.builder()
                    .exchange(exchange)
                    .market(code)
                    .build())
        );
    }
}
