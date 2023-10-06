package com.socket.client.bithumb.request;

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
    @JsonProperty("tickTypes")
    private List<String> tickTypes;

    @Builder
    public BithumbRequest(String type, List<String> symbols, List<String> tickTypes) {
        this.type = type;
        this.symbols = symbols;
        this.tickTypes = tickTypes;
    }
}
