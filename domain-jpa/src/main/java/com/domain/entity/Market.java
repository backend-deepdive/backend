package com.domain.entity;

import com.core.Exchange;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Exchange exchange;
    @Column(nullable = false, unique = true)
    private String market;

    @Builder
    public Market(Exchange exchange, String market) {
        this.exchange = exchange;
        this.market = market;
    }
}
