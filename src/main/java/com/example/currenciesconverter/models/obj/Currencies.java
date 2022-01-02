package com.example.currenciesconverter.models.obj;

import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Map;

public class Currencies {

    private Map<String,String> query;
    private Map<String,Double> data;

    public Currencies() {
    }

    public Currencies(Map<String, String> query, Map<String, Double> data) {
        this.query = query;
        this.data = data;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }
}
