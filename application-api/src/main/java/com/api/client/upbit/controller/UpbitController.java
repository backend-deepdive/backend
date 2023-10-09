package com.api.client.upbit.controller;

import com.api.client.upbit.controller.dto.MarketInfo;
import com.api.client.upbit.service.UpbitService;
import com.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upbit")
public class UpbitController {

    private final WebClient webClient;
    private final UpbitService upbitService;

    @Autowired
    public UpbitController(WebClient.Builder webClientBuilder, UpbitService upbitService) {
        this.webClient = webClientBuilder.baseUrl("https://api.upbit.com").build();
        this.upbitService = upbitService;
    }

    @GetMapping(value = "/market", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAllMarkets() {
        Flux<MarketInfo> markets =  getAllMarkets();
        List<String> codes = new ArrayList<>();
        markets
            .subscribe(
                marketInfo -> {
                    codes.add(marketInfo.getMarket());
                },
                throwable -> {
                    // 에러 처리 로직
                    System.err.println("Error: " + throwable.getMessage());
                },
                () -> {
                    upbitService.save(Exchange.UPBIT, codes);
                }
            );
    }

    private Flux<MarketInfo> getAllMarkets() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/market/all")
                        .queryParam("isDetails", false)
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .bodyToFlux(MarketInfo.class);
    }
}

