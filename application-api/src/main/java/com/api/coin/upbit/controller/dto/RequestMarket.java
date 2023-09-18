package com.api.coin.upbit.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class RequestMarket {
    @JsonProperty("market")
    private String market;
    @JsonProperty("korean_name")
    private String koreanName;
    @JsonProperty("english_name")
    private String englishName;
}