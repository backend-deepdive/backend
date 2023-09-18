package com.api.coin.upbit.controller;

import com.api.coin.upbit.controller.dto.RequestMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/upbit")
public class UpbitApiController {

    private final WebClient webClient;

    @Autowired
    public UpbitApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.upbit.com").build();
    }

    @GetMapping(value = "/market/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RequestMarket> getUpbitTicks(@RequestParam("isDetails") final Boolean isDetail) {
       return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/market/all")
                        .queryParam("isDetails", isDetail)  // Request parameter 추가
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .bodyToFlux(RequestMarket.class);
    }
}

