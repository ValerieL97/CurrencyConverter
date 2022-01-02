package com.example.currenciesconverter.models;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ChartEntity {

    private String currency;
    private LinkedList<Double> rates;

    public ChartEntity(String currency, LinkedList<Double> rates) {
        this.currency = currency;
        this.rates = rates;
    }

    public ChartEntity() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LinkedList<Double> getRates() {
        return rates;
    }

    public void setRates(LinkedList<Double> rates) {
        this.rates = rates;
    }
}
