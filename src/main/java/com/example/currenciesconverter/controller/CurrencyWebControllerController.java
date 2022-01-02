package com.example.currenciesconverter.controller;

import com.example.currenciesconverter.service.CurrencyDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyWebControllerController {


    @GetMapping("/")
    public String getPage() {
        return "index";
    }
}
