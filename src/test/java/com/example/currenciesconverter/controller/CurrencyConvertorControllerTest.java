package com.example.currenciesconverter.controller;

import com.example.currenciesconverter.models.CurrenciesInfo;
import com.example.currenciesconverter.models.CurrencyConversion;
import com.example.currenciesconverter.models.TableEntity;
import com.example.currenciesconverter.repo.CurrencyRepo;
import com.example.currenciesconverter.service.CurrencyConvertorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CurrencyConvertorControllerTest {

    @Mock
    private CurrencyRepo repository;


    private CurrencyConvertorService currencyConvertorService;

    @Autowired
    @InjectMocks
    private CurrencyConvertorController subject;

    @Before
    public void setUp(){
        subject = new CurrencyConvertorController(currencyConvertorService);
    }



    @Test
    public void conversionShouldBeSuccessful() {

        RestTemplate restTemplate = new RestTemplate();

        CurrenciesInfo currencyEUR = new CurrenciesInfo("EUR", 1.0);
        CurrenciesInfo currencyMDL = new CurrenciesInfo("MDL", 20.15);

        Mockito.when(repository.findById("EUR")).thenReturn(Optional.of(currencyEUR));
        Mockito.when(repository.findById("MDL")).thenReturn(Optional.of(currencyMDL));

        CurrencyConversion  currencyConversion = new CurrencyConversion("MDL", "EUR", 200);

        ResponseEntity<Double> responseEntity = restTemplate.postForEntity("http://localhost:8080/result",
                currencyConversion, Double.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getDataChartShouldBeSuccessful() {

        RestTemplate restTemplate = new RestTemplate();

        CurrenciesInfo currencyEUR = new CurrenciesInfo("EUR", 1.0);
        CurrenciesInfo currencyMDL = new CurrenciesInfo("MDL", 20.15);

        Mockito.when(repository.findById("EUR")).thenReturn(Optional.of(currencyEUR));
        Mockito.when(repository.findById("MDL")).thenReturn(Optional.of(currencyMDL));

        CurrencyConversion  currencyConversion = new CurrencyConversion("MDL", "EUR", 200);

        ResponseEntity<LinkedList> responseEntity = restTemplate.
                postForEntity("http://localhost:8080/data-chart", currencyConversion, LinkedList.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getDataTableShouldBeSuccessful() {

        RestTemplate restTemplate = new RestTemplate();

        CurrenciesInfo currencyCAD = new CurrenciesInfo("CAD", 1.0);
        CurrenciesInfo currencyUSD = new CurrenciesInfo("USD", 20.15);
        CurrenciesInfo currencyGBP = new CurrenciesInfo("GBP", 1.0);
        CurrenciesInfo currencyAUD = new CurrenciesInfo("AUD", 20.15);
        CurrenciesInfo currencyNZD = new CurrenciesInfo("NZD", 20.15);

        Mockito.when(repository.findById("CAD")).thenReturn(Optional.of(currencyCAD));
        Mockito.when(repository.findById("GBP")).thenReturn(Optional.of(currencyGBP));
        Mockito.when(repository.findById("AUD")).thenReturn(Optional.of(currencyAUD));
        Mockito.when(repository.findById("USD")).thenReturn(Optional.of(currencyUSD));
        Mockito.when(repository.findById("NZD")).thenReturn(Optional.of(currencyNZD));


        ResponseEntity<LinkedList> responseEntity = restTemplate.
                getForEntity("http://localhost:8080/data-table", LinkedList.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getDataForNewRowShouldBeSuccessful() {

        RestTemplate restTemplate = new RestTemplate();

        CurrenciesInfo currencyCAD = new CurrenciesInfo("MDL", 20.0);

        Mockito.when(repository.findById("MDL")).thenReturn(Optional.of(currencyCAD));

        String currency = "012345678910MDL12";


        ResponseEntity<TableEntity> responseEntity = restTemplate.
                postForEntity("http://localhost:8080/new-row", currency,TableEntity.class);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

}