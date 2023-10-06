package com.api.coin.upbit.service;

import com.api.coin.upbit.controller.dto.MarketInfo;
import com.domain.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class UpbitService {
    private final MarketService marketService;
    public void saveAllMarket(Flux<MarketInfo> markets) {
    }
}

