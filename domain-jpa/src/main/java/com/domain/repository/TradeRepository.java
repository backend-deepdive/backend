package com.domain.repository;

import com.domain.entity.Trades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trades, Long> {
}