package com.domain.service;

import com.core.Exchange;
import com.domain.entity.Ticker;
import com.domain.entity.Trade;
import com.domain.global.ProxyJpaInterface;
import com.domain.repository.TradeRepository;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService implements ProxyJpaInterface {

    private final TradeRepository tradeRepository;

    public void save(Exchange exchange, HashMap<String, Object> data) {
        HashMap<String, Object> contentData = (HashMap<String, Object>) data.get("content");
        List<HashMap<String, Object>> contentList = (List<HashMap<String, Object>>) contentData.get("list");
        for (HashMap<String, Object> content : contentList) {
            tradeRepository.save(Trade.toEntity(exchange, content));
        }
    }

}
