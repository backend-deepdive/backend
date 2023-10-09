package com.domain.global;

import com.core.Exchange;
import com.domain.service.OrderBookService;
import com.domain.service.TickerService;
import com.domain.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CoinManagerImpl implements CoinManagerIF {
    private final TickerService tickerService;
    private final TradeService tradeService;
    private final OrderBookService orderBookService;

    @Override
    public void save(Exchange exchange, HashMap<String, Object> data) {
        String type = data.get("type").toString();

        switch (type) {
            case "ticker":
                tickerService.save(exchange, data);
                break;
            case "trade":
            case "transaction":
                tradeService.save(exchange, data);
                break;
            case "orderbook":
            case "orderbookdepth":
                orderBookService.save(exchange, data);
                break;
        }
    }
}
