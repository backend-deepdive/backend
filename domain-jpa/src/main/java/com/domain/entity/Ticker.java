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
//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Exchange exchange;
//    @Column(nullable = false)
    private String market;
//    @Column(nullable = false)
    private Double openPrice;
//    @Column(nullable = false)
    private Double highPrice;
//    @Column(nullable = false)
    private Double lowPrice;
//    @Column(nullable = false)
    private Double prevClosingPrice;
//    @Column(nullable = false)
    private Double accTradeVolume;
//    @Column(nullable = false)
    private Double accTradePrice;
//    @Column(nullable = false)
    private LocalDateTime incomeDatetime;

    @Builder
    public Ticker(Exchange exchange, String market, Double openPrice, Double highPrice, Double lowPrice, Double prevClosingPrice, Double accTradeVolume, Double accTradePrice, LocalDateTime incomeDatetime) {
        this.exchange = exchange;
        this.market = market;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.prevClosingPrice = prevClosingPrice;
        this.accTradeVolume = accTradeVolume;
        this.accTradePrice = accTradePrice;
        this.incomeDatetime = incomeDatetime;
    }

    public static Ticker toEntity(Exchange exchange, HashMap<String, Object> data) {
        Ticker ticker =
                Ticker.builder()
                        .exchange(exchange)
                        .build();
        System.out.println(data);

        data.forEach((key, value) -> {
            switch (key) {
                /* key 순서
                * upbit
                * bithumb
                * */
                case "code":
                case "symbol":
                    ticker.setMarket((String) value);
                    break;
                case "opening_price":
                case "openPrice:":
                    ticker.setOpenPrice(Double.parseDouble((String) value));
                    break;
                case "high_price":
                case "highPrice":
                    ticker.setHighPrice(Double.parseDouble((String) value));
                    break;
                case "low_price":
                case "lowPrice":
                    ticker.setLowPrice(Double.parseDouble((String) value));
                    break;
                case "prev_closing_price":
                case "prevClosePrice":
                    ticker.setPrevClosingPrice(Double.parseDouble((String) value));
                    break;
                case "acc_trade_volume":
                case "volume":
                    ticker.setAccTradeVolume(Double.parseDouble((String) value));
                    break;
                case "acc_trade_price":
                case "value":
                    ticker.setAccTradePrice(Double.parseDouble((String) value));
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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    LocalDateTime localDateTime = LocalDateTime.parse(value + (String) data.get("time"), formatter);
                    ticker.setIncomeDatetime(localDateTime);
                default: break;
            }
        });
        return ticker;
    }
}
