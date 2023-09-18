package com.domain.global;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.domain.entity")
@EnableJpaRepositories("com.domain.repository")
@Configuration
public class ScanComponents {
}