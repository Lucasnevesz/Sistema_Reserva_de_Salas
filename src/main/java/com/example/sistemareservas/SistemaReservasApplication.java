package com.example.sistemareservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import jakarta.servlet.Filter;

@SpringBootApplication
public class SistemaReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaReservasApplication.class, args);
    }

    @Bean
    public Filter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
