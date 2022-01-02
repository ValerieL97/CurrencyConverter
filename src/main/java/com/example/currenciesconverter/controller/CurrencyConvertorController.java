package com.example.currenciesconverter.controller;


import com.example.currenciesconverter.models.ChartEntity;
import com.example.currenciesconverter.models.CurrenciesInfo;
import com.example.currenciesconverter.models.CurrencyConversion;
import com.example.currenciesconverter.models.TableEntity;
import com.example.currenciesconverter.service.CurrencyConvertorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CurrencyConvertorController {

    private final CurrencyConvertorService currencyConvertorService;

    public CurrencyConvertorController(CurrencyConvertorService currencyConvertorService) {
        this.currencyConvertorService = currencyConvertorService;
    }

    // get all currencies
    @RequestMapping(value = "/currencies", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<List<CurrenciesInfo>> getAllCurrencies() {
        return new ResponseEntity<>(currencyConvertorService.getAllCurrencies(), HttpStatus.OK);
    }

    //get data for table
    @RequestMapping(value = "/data-table", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LinkedList<TableEntity>> getDataForTable() {
        LinkedList<TableEntity> data = new LinkedList<>();
        data.add(currencyConvertorService.getDataForTable("USD"));
        data.add(currencyConvertorService.getDataForTable("GBP"));
        data.add(currencyConvertorService.getDataForTable("CAD"));
        data.add(currencyConvertorService.getDataForTable("AUD"));
        data.add(currencyConvertorService.getDataForTable("NZD"));
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    //add new row to table
    @RequestMapping(value = "/new-row", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<TableEntity> getDataForNewRow(@RequestBody String currency) {
        String currency1= currency.substring(13,16);
        TableEntity data = currencyConvertorService.getDataForTable(currency1);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    //get data for chart
    @RequestMapping(value = "/data-chart", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<LinkedList<ChartEntity>> getDataForChart(
            @RequestBody CurrencyConversion currencyConversion) {
        LinkedList<ChartEntity> list = currencyConvertorService.getDataForVisualization(currencyConversion.getFrom(), currencyConversion.getTo());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    //conversion result
    @RequestMapping(value = "/result", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<Double> convert(@RequestBody CurrencyConversion currencyConversion) {
        return new ResponseEntity<>(currencyConvertorService.conversion(currencyConversion), HttpStatus.OK);
    }







}


