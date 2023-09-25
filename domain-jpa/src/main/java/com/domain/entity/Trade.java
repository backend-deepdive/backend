package com.domain.entity;

import com.core.Exchange;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Exchange exchange;
    private String symbol;
    private String buySellGb;
    private String contPrice;
    private String contAmt;
    private String contDtm;
    private String updn;

    @Builder
    public Trade(Exchange exchange, String symbol, String buySellGb, String contPrice, String contAmt, String contDtm, String updn) {
        this.exchange = exchange;
        this.symbol = symbol;
        this.buySellGb = buySellGb;
        this.contPrice = contPrice;
        this.contAmt = contAmt;
        this.contDtm = contDtm;
        this.updn = updn;
    }

    public static Trade toEntity(Exchange exchange, HashMap<String, Object> data) {
        Trade trade =
            Trade.builder()
                .exchange(exchange)
                .build();
//{type=transaction, content={list=[{updn=dn, contDtm=2023-09-25 17:01:43.101299, contAmt=1044742.5000, contQty=0.0295, contPrice=35415000, buySellGb=1, symbol=BTC_KRW}]}}
        data.forEach((key, value) ->{
            switch (key) {
                case "symbol":
                case "upbit1":
                    trade.setSymbol((String) value);
                    break;
                case "buySellGb":
                case "upbit2":
                    trade.setBuySellGb((String) value);
                    break;
                case "contPrice":
                case "upbit3":
                    trade.setContPrice((String) value);
                    break;
                case "contAmt":
                case "upbit4":
                    trade.setContAmt((String) value);
                    break;
                case "contDtm":
                case "upbit5":
                    trade.setContDtm((String) value);
                    break;
                case "updn":
                case "upbit6":
                    trade.setUpdn((String) value);
                    break;
                default:
                    break;
            }
        });
        return trade;
    }
}
