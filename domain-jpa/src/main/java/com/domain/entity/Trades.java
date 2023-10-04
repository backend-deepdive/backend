package com.domain.entity;

import com.core.Exchange;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Exchange exchange;
    private String market;
    private String askBid;
    private Double tradePrice;
    private Double tradeVolume;
    private Double tradeAmt;
    private LocalDateTime tradeDateTime;
    private String changes;

    @Builder
    public Trades(Exchange exchange) {
        this.exchange = exchange;
    }

    public static Trades toEntity(Exchange exchange, HashMap<String, Object> data) {
        Trades trade =
                Trades.builder()
                        .exchange(exchange)
                        .build();

        data.forEach((key, value) -> {
            switch (key) {
                /* key 순서
                 * upbit
                 * bithumb
                 * */
                // 마켓 코드
                case "code":
                case "symbol":
                    trade.setMarket((String) value);
                    break;
                // 체결 구분
                case "ask_bid":
                case "buySellGb":
                    trade.setAskBid((String) value);
                    break;
                // 체결 가격
                case "trade_price":
                case "contPrice":
                    trade.setTradePrice(Double.parseDouble((String) value));
                    break;
                // 체결 수량
                case "trade_volume":
                case "contQty":
                    trade.setTradeVolume(Double.parseDouble((String) value));
                    break;
                // 체결 금액
                case "contAmt":
                    trade.setTradeAmt(Double.parseDouble((String) value));
                    break;
                // 체결 시각
                case "trade_date":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(value + (String) data.get("trade_time"), formatter);
                    trade.setTradeDateTime(localDateTime);
                    break;
                case "contDtm":
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    LocalDateTime localDateTime2 = LocalDateTime.parse((String) value, formatter2);
                    trade.setTradeDateTime(localDateTime2);
                    break;
                // 변화(상승 / 하락)
                case "changes":
                case "updn":
                    trade.setChanges((String) value);
                    break;
                default: break;
            }
        });

        return trade;
    }
}
