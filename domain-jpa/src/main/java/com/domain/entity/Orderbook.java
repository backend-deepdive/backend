package com.domain.entity;

import com.core.Exchange;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orderbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Exchange exchange;
    private String market;
    @OneToMany(mappedBy = "orderBook", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderBookUnit> orderBookUnits = new ArrayList<>();
    private LocalDateTime datetime;

    @Builder
    public Orderbook(Exchange exchange) {
        this.exchange = exchange;
    }

    public void addOrderBookUnits(OrderBookUnit orderBookUnit) {
        this.orderBookUnits.add(orderBookUnit);
    }
}
