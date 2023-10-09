package com.domain.mapper;

import com.core.Exchange;
import com.domain.entity.OrderBookUnit;
import com.domain.entity.Orderbook;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

@Component
public class OrderBookMapper {
    public Orderbook toEntity(Exchange exchange, HashMap<String, Object> data) {
        Orderbook orderbook = Orderbook.builder()
                .exchange(exchange)
                .build();

        data.forEach((key, value) -> {
            switch (key) {
                /* key 순서
                 * upbit
                 * bithumb
                 * */
                case "code":
                    orderbook.setMarket(value.toString());
                    break;
                case "orderbook_units":
                    List<HashMap<String, Double>> units = (List<HashMap<String, Double>>) value;
                    units.forEach(unit -> {
                        OrderBookUnit askOrderbook = OrderBookUnit.builder()
                                .orderType("ask")
                                .orderBook(orderbook)
                                .price(unit.get("ask_price"))
                                .quantity(unit.get("ask_size"))
                                .build();

                        OrderBookUnit bidOrderbook = OrderBookUnit.builder()
                                .orderType("bid")
                                .orderBook(orderbook)
                                .price(unit.get("bid_price"))
                                .quantity(unit.get("bid_size"))
                                .build();

                        orderbook.addOrderBookUnits(askOrderbook);
                        orderbook.addOrderBookUnits(bidOrderbook);
                    });
                    break;
                case "list":
                    List<HashMap<String, Object>> depths = (List<HashMap<String, Object>>) value;
                    depths.forEach(depth -> {
                        orderbook.setMarket(depth.get("symbol").toString());

                        OrderBookUnit orderBookUnit = OrderBookUnit.builder()
                                .orderType(depth.get("orderType").toString())
                                .orderBook(orderbook)
                                .price(Double.parseDouble(depth.get("price").toString()))
                                .quantity(Double.parseDouble(depth.get("quantity").toString()))
                                .total(Integer.parseInt(depth.get("total").toString()))
                                .build();

                        orderbook.addOrderBookUnits(orderBookUnit);
                    });
                    break;
                 case "timestamp":
                    LocalDateTime timestampToDatetime =
                            LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli((Long) value),
                                    TimeZone.getDefault().toZoneId()
                            );
                    orderbook.setDatetime(timestampToDatetime);
                    break;
                    // Unix 시간을 LocalDateTime 형식으로 변환해야되는데 할 수 있는 사람
                    // Incorrect datetime value: '55740-07-13 06:33:21.385'
                    /*                case "datetime":
                    long unixTimeMillis = Long.parseLong(value.toString());
                    Instant instant = Instant.ofEpochMilli(unixTimeMillis);
                    LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    orderbook.setDatetime(localDateTime);
                    break;*/
            }
        });

        return orderbook;
    }
}
