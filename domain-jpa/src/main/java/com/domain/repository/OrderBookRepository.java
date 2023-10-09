package com.domain.repository;

import com.domain.entity.Orderbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends JpaRepository<Orderbook, Long> {
}