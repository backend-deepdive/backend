package com.api.controller.coin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coin")
public class CoinController {
    @GetMapping
    public String hello() {
        return "hello";
    }

}
