package com.example.currenciesconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrenciesConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrenciesConverterApplication.class, args);
    }

}
