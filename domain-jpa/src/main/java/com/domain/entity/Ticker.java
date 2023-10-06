package com.domain.entity;

import com.core.Exchange;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.TimeZone;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Exchange exchange;
    private String market;
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double prevClosingPrice;
    private Double accTradeVolume;
    private Double accTradePrice;
    private LocalDateTime incomeDatetime;

    @Builder
    public Ticker(Exchange exchange) {
        this.exchange = exchange;
    }

    public static Ticker toEntity(Exchange exchange, HashMap<String, Object> data) {
        Ticker ticker =
                Ticker.builder()
                        .exchange(exchange)
                        .build();
        data.forEach((key, value) -> {
            switch (key) {
                /* key 순서
                * upbit
                * bithumb
                * */
                case "code":
                case "symbol":
                    ticker.setMarket(value.toString());
                    break;
                case "opening_price":
                case "openPrice":
                    ticker.setOpenPrice(Double.parseDouble(value.toString()));
                    break;
                case "high_price":
                case "highPrice":
                    ticker.setHighPrice(Double.parseDouble(value.toString()));
                    break;
                case "low_price":
                case "lowPrice":
                    ticker.setLowPrice(Double.parseDouble(value.toString()));
                    break;
                case "prev_closing_price":
                case "prevClosePrice":
                    ticker.setPrevClosingPrice(Double.parseDouble(value.toString()));
                    break;
                case "acc_trade_volume":
                case "volume":
                    ticker.setAccTradeVolume(Double.parseDouble(value.toString()));
                    break;
                case "acc_trade_price":
                case "value":
                    ticker.setAccTradePrice(Double.parseDouble(value.toString()));
                    break;
                case "timestamp":
                    LocalDateTime incomeDateTime =
                            LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli((Long) value),
                                    TimeZone.getDefault().toZoneId()
                            );
                    ticker.setIncomeDatetime(incomeDateTime);
                    break;
                case "date":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime localDateTime = LocalDateTime.parse(value + (String) data.get("time"), formatter);
                    ticker.setIncomeDatetime(localDateTime);
                default: break;
            }
        });

        return ticker;
    }
}
