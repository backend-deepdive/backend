package com.worker.worker.socket.bithumb.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Setter;

@Setter
public class BithumbTickerRequest {
    @JsonProperty("type")
    private String type;
    @JsonProperty("symbols")
    private List<String> symbols;

    @Builder
    public BithumbTickerRequest(String type, List<String> symbols) {
        this.type = type;
        this.symbols = symbols;
    }
}
