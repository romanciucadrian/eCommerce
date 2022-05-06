package com.shopme.admin.service;

import com.shopme.admin.repository.BrandRepository;
import com.shopme.admin.service.impl.IBrandService;
import com.shopme.common.entity.Brand;

import java.util.List;

public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<Brand> listAll() {
        return (List<Brand>) brandRepository.findAll();
    }
}
