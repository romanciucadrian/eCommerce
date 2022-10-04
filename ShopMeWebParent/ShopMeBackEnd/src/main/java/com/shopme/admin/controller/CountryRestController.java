package com.shopme.admin.controller;

import com.shopme.admin.repository.CountryRepository;
import com.shopme.common.entity.Country;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryRestController {

    private final CountryRepository countryRepository;

    public CountryRestController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries/list")
    public List<Country> listAll() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    @PostMapping("/countries/save")
    public String save(@RequestBody Country country) {
        Country savedCountry = countryRepository.save(country);

        return String.valueOf(savedCountry.getId());
    }

    @GetMapping("/countries/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        countryRepository.deleteById(id);
    }
}
