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
}
