package com.example.currenciesconverter.service;

import com.example.currenciesconverter.models.ChartEntity;
import com.example.currenciesconverter.models.CurrenciesInfo;
import com.example.currenciesconverter.models.CurrencyConversion;
import com.example.currenciesconverter.models.TableEntity;
import com.example.currenciesconverter.repo.CurrencyRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurrencyConvertorService {

    private final CurrencyRepo currencyRepo;

    public CurrencyConvertorService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    //--------------------------CALCULATOR---------------------------------//

    //convert one currency to another
    public Double conversion(CurrencyConversion currencyConversion){

        Optional<CurrenciesInfo> from = currencyRepo.findById(currencyConversion.getFrom());
        Optional<CurrenciesInfo> to = currencyRepo.findById(currencyConversion.getTo());

        if(from.isEmpty() || to.isEmpty()){
            return null;
        }

        Double fromValue = from.get().getValue();
        Double toValue = to.get().getValue();
        Double result = toValue * (currencyConversion.getValue()/fromValue);

        if(result < 0) {
            return 0.0;
        }

        return Math.round(result*10000.0)/10000.0;
    }


    //return the list of alphabetically ordered currencies
    public List<CurrenciesInfo> getAllCurrencies() {
        List<CurrenciesInfo> list = currencyRepo.findAll();
        if (list.size() > 0) {
            Collections.sort(list, new Comparator<CurrenciesInfo>() {
                @Override
                public int compare(final CurrenciesInfo object1, final CurrenciesInfo object2) {
                    return object1.getCurrency().compareTo(object2.getCurrency());
                }
            });
        }
        return list;
    }


    //------------------------------CHART-----------------------------------//

    //return data for chart
    public LinkedList<ChartEntity> getDataForVisualization(String currency1, String currency2) {
        LinkedList<ChartEntity> list = new LinkedList<>();

        Optional<CurrenciesInfo> currenciesInfo1 = currencyRepo.findById(currency1);
        Optional<CurrenciesInfo> currenciesInfo2 = currencyRepo.findById(currency2);

        if(currenciesInfo1.isEmpty() || currenciesInfo2.isEmpty()) {
            return null;
        }

        LinkedList<Double> list1 = new LinkedList<>(currenciesInfo1.get().getRates().values());
        LinkedList<Double> list2 = new LinkedList<>(currenciesInfo2.get().getRates().values());
        ChartEntity chartEntity1 = new ChartEntity(currenciesInfo1.get().getCurrency(),
                list1);
        ChartEntity chartEntity2 = new ChartEntity(currenciesInfo2.get().getCurrency(),
                list2);

        list.add(chartEntity1);
        list.add(chartEntity2);

        return list;
    }


    //-------------------------------TABLE-------------------------------------//

    //return data for table
    public TableEntity getDataForTable(String currency) {

        Optional<CurrenciesInfo> currenciesInfo = currencyRepo.findById(currency);

        if(currenciesInfo.isEmpty()) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        String yesterday = dateFormat.format(date);

        double num1 = currenciesInfo.get().getValue();
        double num2 = currenciesInfo.get().getRates().get(yesterday);

        TableEntity tableEntity = new TableEntity(currenciesInfo.get().getCurrency(),
                Math.round(num1*10000.0)/10000.0,calculateDifference(num1,num2),calculatePercentage(num1,num2));



        return tableEntity;

    }


    public String calculatePercentage(double todayValue, double yesterdayValue) {

        double result = ((todayValue-yesterdayValue)/yesterdayValue) * 100;

        String resultStr = "0.0";

        if(result > 0.0) {
            resultStr = "+" + Math.round(result*10000.0)/10000.0 + "%";
        } else {
            resultStr = Math.round(result*10000.0)/10000.0 + "%";
        }
        return resultStr;
    }

    public String calculateDifference(double todayValue, double yesterdayValue){

        double result = todayValue - yesterdayValue;

        String resultStr = "0.0";

        if(result > 0.0) {
            resultStr = "+" + Math.round(result*10000.0)/10000.0;
        } else {
            resultStr = ""+ Math.round(result*10000.0)/10000.0;
        }
        return resultStr;
    }

}
