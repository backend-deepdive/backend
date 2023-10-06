package com.api.client.bithumb.controller;

import com.api.client.upbit.service.UpbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;

@RestController
@RequestMapping("/bithumb")
public class BithumbController {

    private final WebClient webClient;

    @Autowired
    public BithumbController(WebClient.Builder webClientBuilder, UpbitService upbitService) {
        this.webClient = webClientBuilder.baseUrl("https://api.bithumb.com/public").build();
    }

    @GetMapping(value = "/market", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAllMarkets() {
        Flux<HashMap> markets =  getAllMarkets();
        markets
            .subscribe(
                    marketInfo -> {
                        System.out.println(marketInfo);
                    },
                    throwable -> {
                        // 에러 처리 로직
                        System.err.println("Error: " + throwable.getMessage());
                    },
                    () -> {
                        // Flux가 완료되면 호출되는 로직
                        System.out.println("Processing completed.");
                    }
            );
    }

    private Flux<HashMap> getAllMarkets() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ticker/ALL_KRW")
                        .build())
                .header("accept", "application/json")
                .retrieve()
                .bodyToFlux(HashMap.class);
    }
}

