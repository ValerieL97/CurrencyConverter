package com.example.currenciesconverter.models;

public class TableEntity {

    private String currency;
    private Double amount;
    private String difference;
    private String differencePercentage;

    public TableEntity() {
    }

    public TableEntity(String currency, Double amount, String difference, String differencePercentage) {
        this.currency = currency;
        this.amount = amount;
        this.difference = difference;
        this.differencePercentage = differencePercentage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public String getDifferencePercentage() {
        return differencePercentage;
    }

    public void setDifferencePercentage(String differencePercentage) {
        this.differencePercentage = differencePercentage;
    }
}
