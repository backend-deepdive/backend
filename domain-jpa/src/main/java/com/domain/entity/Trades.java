package com.domain.entity;

import com.core.Exchange;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
