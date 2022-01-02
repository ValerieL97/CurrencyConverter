package com.example.currenciesconverter.models;

public class CurrencyConversion {

    private String from;
    private String to;
    private double value;

    public CurrencyConversion() {
    }

    public CurrencyConversion(String from, String to, double value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
