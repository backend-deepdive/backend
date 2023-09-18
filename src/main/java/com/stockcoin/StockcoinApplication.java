package com.stockcoin;

import com.api.api.controller.coin.upbit.UpbitApiManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.api"})
public class StockcoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockcoinApplication.class, args);
    }
}
