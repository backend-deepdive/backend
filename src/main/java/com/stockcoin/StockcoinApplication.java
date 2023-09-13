package com.stockcoin;

import com.api.global.config.ApplicationApiConfig;
import com.api.socket.upbit.UpbitWsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationApiConfig.class})
public class StockcoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockcoinApplication.class, args);

        UpbitWsClient client = new UpbitWsClient();
        client.connect();
    }
}
