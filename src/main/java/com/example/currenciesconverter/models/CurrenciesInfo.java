package com.example.currenciesconverter.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.LinkedHashMap;
import java.util.Map;

@RedisHash("CurrenciesC")
public class CurrenciesInfo {

    @Id
    private String currency;
    private double value;
    private LinkedHashMap<String,Double> rates;

    public CurrenciesInfo() {
    }

    public CurrenciesInfo(String currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public CurrenciesInfo(String currency, double value, LinkedHashMap<String, Double> rates) {
        this.currency = currency;
        this.value = value;
        this.rates = rates;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LinkedHashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(LinkedHashMap<String, Double> rates) {
        this.rates = rates;
    }
}
