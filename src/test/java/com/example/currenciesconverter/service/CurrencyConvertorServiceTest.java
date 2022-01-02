package com.example.currenciesconverter.service;

import com.example.currenciesconverter.models.ChartEntity;
import com.example.currenciesconverter.models.CurrenciesInfo;
import com.example.currenciesconverter.models.CurrencyConversion;
import com.example.currenciesconverter.models.TableEntity;
import com.example.currenciesconverter.repo.CurrencyRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CurrencyConvertorServiceTest {

    @MockBean
    private CurrencyRepo currencyRepo;

    @Autowired
    @InjectMocks
    private CurrencyConvertorService subject;


    //----------------------CALCULATOR----------------------//


    @Test
    public void getAllCurrenciesTestEmpty() {
        Mockito.when(this.currencyRepo.findAll()).thenReturn(Arrays.asList());
        List<CurrenciesInfo> currencies = this.subject.getAllCurrencies();
        Assert.assertTrue(currencies.isEmpty());
    }

    @Test
    public void conversionShouldReturnZeroWhenNegativeValue() {
        CurrenciesInfo currencyEUR = new CurrenciesInfo("EUR", 1.0);
        CurrenciesInfo currencyUSD = new CurrenciesInfo("RON", 4.8);


        Mockito.when(currencyRepo.findById("EUR")).thenReturn(Optional.of(currencyEUR));
        Mockito.when(currencyRepo.findById("RON")).thenReturn(Optional.of(currencyUSD));

        CurrencyConversion conversionCurrency = new CurrencyConversion("EUR", "RON", -10.0);

        Double result = subject.conversion(conversionCurrency);

        Double expected = 0.0;

        Assert.assertEquals(expected,result);
    }

    @Test
    public void conversionShouldReturnNullWhenCurrencyDoesNotExist() {
        CurrenciesInfo currencyUSD = new CurrenciesInfo("USD", 0.8);

        Mockito.when(currencyRepo.findById("RON")).thenReturn(Optional.empty());
        Mockito.when(currencyRepo.findById("USD")).thenReturn(Optional.of(currencyUSD));

        CurrencyConversion currencyConversion = new CurrencyConversion("RON", "USD", 0.0);

        Double result = subject.conversion(currencyConversion);

        Assert.assertNull(result);
    }

    @Test
    public void conversionShouldReturnADoubleValueWithFourDecimalPlaces() {
        CurrenciesInfo currencyEUR = new CurrenciesInfo("EUR", 1.0);
        CurrenciesInfo currencyUSD = new CurrenciesInfo("RON", 4.834245);


        Mockito.when(currencyRepo.findById("EUR")).thenReturn(Optional.of(currencyEUR));
        Mockito.when(currencyRepo.findById("USD")).thenReturn(Optional.of(currencyUSD));

        CurrencyConversion currencyConversion = new CurrencyConversion("EUR", "USD", 75.0);

        Double result = subject.conversion(currencyConversion);

        Double expected = 362.5684;
        Assert.assertEquals(expected,result);
    }


    @Test
    public void getAllCurrenciesTestAreSorted () {

        CurrenciesInfo currencyMDL = new CurrenciesInfo ("MDL", 20.0);
        CurrenciesInfo  currencyEUR = new CurrenciesInfo ("EUR", 1.0);
        CurrenciesInfo  currencyRON = new CurrenciesInfo ("RON", 4.8);
        CurrenciesInfo  currencyUSD = new CurrenciesInfo ("USD", 0.8);

        Mockito.when(currencyRepo.findAll())
                .thenReturn(Arrays.asList(currencyMDL, currencyEUR, currencyUSD, currencyRON));

        List<CurrenciesInfo> currencies = subject.getAllCurrencies();

        Assert.assertEquals(currencies.get(0), currencyEUR);
        Assert.assertEquals(currencies.get(1), currencyMDL);
        Assert.assertEquals(currencies.get(2), currencyRON);
        Assert.assertEquals(currencies.get(3), currencyUSD);

    }

    //---------------------------CHART-------------------------------------//

    @Test
    public void getDataForVisualizationShouldReturnNullWhenCurrencyDoesNotExist() {

        LinkedHashMap<String,Double> map = new LinkedHashMap<>();

        map.put("2021-01-12",1.0);
        map.put("2021-01-13",2.0);
        map.put("2021-01-14",3.0);
        map.put("2021-01-15",4.0);
        map.put("2021-01-16",5.0);
        map.put("2021-01-17",6.0);
        map.put("2021-01-18",7.0);


        CurrenciesInfo currencyMDL = new CurrenciesInfo("MDL",20.0,map);

        Mockito.when(currencyRepo.findById("EUR")).thenReturn(Optional.empty());
        Mockito.when(currencyRepo.findById("MDL")).thenReturn(Optional.of(currencyMDL));


        List<ChartEntity> list = subject.getDataForVisualization("MDL","EUR");


        Assert.assertNull(list);


    }

    @Test
    public void getDataForVisualizationShouldReturnChartEntityRatesInSpecificOrder() {

        LinkedHashMap<String,Double> map = new LinkedHashMap<>();

        map.put("2021-01-12",1.0);
        map.put("2021-01-13",2.0);
        map.put("2021-01-14",3.0);
        map.put("2021-01-15",4.0);
        map.put("2021-01-16",5.0);
        map.put("2021-01-17",6.0);
        map.put("2021-01-18",7.0);


        CurrenciesInfo currencyEUR = new CurrenciesInfo("EUR",1.0,map);
        CurrenciesInfo currencyMDL = new CurrenciesInfo("MDL",20.0,map);

        Mockito.when(currencyRepo.findById("EUR")).thenReturn(Optional.of(currencyEUR));
        Mockito.when(currencyRepo.findById("MDL")).thenReturn(Optional.of(currencyMDL));


        List<ChartEntity> list = subject.getDataForVisualization("MDL","EUR");


        Assert.assertEquals(list.get(0).getRates().get(0), new Double(1.0));
        Assert.assertEquals(list.get(0).getRates().get(1), new Double(2.0));
        Assert.assertEquals(list.get(0).getRates().get(2), new Double(3.0));
        Assert.assertEquals(list.get(0).getRates().get(3), new Double(4.0));
        Assert.assertEquals(list.get(0).getRates().get(4), new Double(5.0));
        Assert.assertEquals(list.get(0).getRates().get(5), new Double(6.0));
        Assert.assertEquals(list.get(0).getRates().get(6), new Double(7.0));
        Assert.assertEquals(list.get(1).getRates().get(0), new Double(1.0));
        Assert.assertEquals(list.get(1).getRates().get(1), new Double(2.0));
        Assert.assertEquals(list.get(1).getRates().get(2), new Double(3.0));
        Assert.assertEquals(list.get(1).getRates().get(3), new Double(4.0));
        Assert.assertEquals(list.get(1).getRates().get(4), new Double(5.0));
        Assert.assertEquals(list.get(1).getRates().get(5), new Double(6.0));
        Assert.assertEquals(list.get(1).getRates().get(6), new Double(7.0));

    }


    //-------------------------------TABLE---------------------------------------//

    @Test
    public void getDataForTableShouldReturnNullWhenCurrencyDoesNotExist() {

        Mockito.when(currencyRepo.findById("MDL")).thenReturn(Optional.empty());

        TableEntity tableEntity = subject.getDataForTable("MDL");

        Assert.assertNull(tableEntity);

    }

    @Test
    public void calculatePercentageNegativeResult() {

        String result = subject.calculatePercentage(1.0,2.0);

        Assert.assertEquals("-50.0%",result);

    }

    @Test
    public void calculatePercentagePositiveResult() {

        String result = subject.calculatePercentage(2.0,1.0);

        Assert.assertEquals("+100.0%",result);

    }


    @Test
    public void calculateDifferencePositiveResult() {

        String result = subject.calculateDifference(2.0,1.0);

        Assert.assertEquals("+1.0",result);

    }

    @Test
    public void calculateDifferenceNegativeResult() {

        String result = subject.calculateDifference(1.0,2.0);

        Assert.assertEquals("-1.0",result);

    }




}