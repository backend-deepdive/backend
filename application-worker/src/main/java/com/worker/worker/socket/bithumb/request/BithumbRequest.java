package com.worker.worker.socket.bithumb.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
public class BithumbRequest {
    @JsonProperty("type")
    private String type;
    @JsonProperty("symbols")
    private List<String> symbols;

    @Builder
    public BithumbRequest(String type, List<String> symbols) {
        this.type = type;
        this.symbols = symbols;
    }
}
