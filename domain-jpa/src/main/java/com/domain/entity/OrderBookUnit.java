package com.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderBookUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_book_id")
    private Orderbook orderBook;
    private String orderType;
    private Double price;
    private Double quantity;
    private Integer total;

    @Builder
    public OrderBookUnit(Orderbook orderBook, String orderType, Double price, Double quantity, Integer total) {
        this.orderBook = orderBook;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }
}
