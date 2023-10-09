package com.api.client.bithumb.controller;

import com.api.client.bithumb.service.BithumbService;
import com.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bithumb")
public class BithumbController {
    private final WebClient webClient;
    private final BithumbService bithumbService;

    @Autowired
    public BithumbController(WebClient.Builder webClientBuilder, BithumbService bithumbService) {
        this.webClient = webClientBuilder.baseUrl("https://api.bithumb.com/public").build();
        this.bithumbService = bithumbService;
    }

    // bithumb 코인거래소에 제공하는 코인 정보 244개
    @GetMapping(value = "/market", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAllMarkets() {
        Flux<HashMap> markets =  getAllMarkets();
        List<String> codes = new ArrayList<>();
        markets
            .subscribe(
                    marketInfo -> {
                        HashMap<String, Object> marketMap = (HashMap<String, Object>) marketInfo.get("data");
                        marketMap.keySet().forEach(code -> {
                            codes.add(code + "_KRW");
                        });
                    },
                    throwable -> {
                        System.err.println("Error: " + throwable.getMessage());
                    },
                    () -> {
                        System.out.println(codes);
                        bithumbService.save(Exchange.BITHUMB, codes);
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

