package com.shopme.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Country;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
    public List<Country> findAllByOrderByNameAsc();
}