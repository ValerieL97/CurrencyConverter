package com.example.currenciesconverter.service;


import com.example.currenciesconverter.models.CurrenciesInfo;
import com.example.currenciesconverter.models.obj.Currencies;
import com.example.currenciesconverter.models.obj.CurrenciesHistory;
import com.example.currenciesconverter.repo.CurrencyRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class CurrencyDataService {

    private final static String LIST_CURRENCIES = "https://freecurrencyapi.net/api/v2/latest?apikey=apikey&base_currency=EUR";
    private final CurrencyRepo currencyRepo;


    public CurrencyDataService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @Scheduled(fixedRate = 24 * 1000 * 60 * 60)
    private void getListCurrencies() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date todayDate = cal.getTime();
        cal.add(Calendar.DATE, -7);
        String from = dateFormat.format(cal.getTime());
        String to = dateFormat.format(todayDate);
        String historical_currencies = "https://freecurrencyapi.net/api/v2/historical?apikey=dca45b40-3ce0-11ec-87a1-1b9e9ee04d96&base_currency=EUR&date_from=" + from +"&date_to="+ to;
        try{
            RestTemplate restTemplate = new RestTemplate();
            Currencies currencies = restTemplate.getForObject(LIST_CURRENCIES, Currencies.class);
            CurrenciesHistory currenciesHistory = restTemplate.getForObject(historical_currencies,CurrenciesHistory.class);

            for(String currency : currencies.getData().keySet()) {
                CurrenciesInfo currenciesInfo = new CurrenciesInfo();
                currencyRepo.deleteById(currency);
                currenciesInfo.setCurrency(currency);
                currenciesInfo.setValue(currencies.getData().get(currency));
                LinkedHashMap<String,Double> rate = new LinkedHashMap<>();
                for(String date : currenciesHistory.getData().keySet()) {
                    rate.put(date,currenciesHistory.getData().get(date).get(currency));
                }
                currenciesInfo.setRates(rate);
                currencyRepo.save(currenciesInfo);
            }

        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }

    }
}
