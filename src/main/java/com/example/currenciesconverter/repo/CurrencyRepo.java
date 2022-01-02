package com.example.currenciesconverter.repo;

import com.example.currenciesconverter.models.CurrenciesInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepo extends CrudRepository<CurrenciesInfo,String> {
    @Override
    List<CurrenciesInfo> findAll();

    @Override
    Optional<CurrenciesInfo> findById(String s);
}
