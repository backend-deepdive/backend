package com.worker.worker.socket.upbit.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
public class UpbitRequest {
    @JsonProperty("ticket")
    private String ticket;
    @JsonProperty("type")
    private String type;
    @JsonProperty("codes")
    private List<String> codes;
    @Builder
    public UpbitRequest(String ticket, String type, List<String> codes) {
        this.ticket = ticket;
        this.type = type;
        this.codes = codes;
    }
}
