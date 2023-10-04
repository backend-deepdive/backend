package com.worker.worker.socket.upbit.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public class UpbitCodeRequest {
    @JsonProperty("type")
    private String type;
    @JsonProperty("codes")
    private List<String> codes;
    @JsonProperty("isOnlySnapshot")
    private final Boolean isOnlySnapshot = false;
    @JsonProperty("isOnlyRealtime")
    private final Boolean isOnlyRealtime = true;

    @Builder
    public UpbitCodeRequest(String type, List<String> codes) {
        this.type = type;
        this.codes = codes;
    }
}
