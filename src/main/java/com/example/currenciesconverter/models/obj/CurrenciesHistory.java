package com.example.currenciesconverter.models.obj;

import java.util.LinkedHashMap;
import java.util.Map;

public class CurrenciesHistory {

    private Map<String,String> query;
    private Map<String,LinkedHashMap<String,Double>> data;

    public CurrenciesHistory() {
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public Map<String, LinkedHashMap<String, Double>> getData() {
        return data;
    }

    public void setData(Map<String, LinkedHashMap<String, Double>> data) {
        this.data = data;
    }
}
