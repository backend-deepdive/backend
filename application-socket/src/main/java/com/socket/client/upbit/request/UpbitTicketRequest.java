package com.socket.client.upbit.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;

@Setter
public class UpbitTicketRequest {
    @JsonProperty("ticket")
    private String ticket;
    @Builder
    public UpbitTicketRequest(String ticket) {
        this.ticket = ticket;
    }
}
