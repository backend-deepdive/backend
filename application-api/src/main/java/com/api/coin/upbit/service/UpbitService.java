package com.api.coin.upbit.service;

import com.api.coin.upbit.controller.dto.MarketInfo;
import com.domain.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class UpbitService {
    private final MarketRepository marketRepository;
    public void saveAllMarket(Flux<MarketInfo> markets) {

    }
}

