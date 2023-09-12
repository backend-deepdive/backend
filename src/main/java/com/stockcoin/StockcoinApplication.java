package com.stockcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StockcoinApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(StockcoinApplication.class, args);
//    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StockcoinApplication.class, args);

        // 생성된 Bean 목록 확인
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

    }

}
