package com.shopme.admin.service;

import com.shopme.admin.error.BrandNotFoundException;
import com.shopme.admin.repository.BrandRepository;
import com.shopme.admin.service.impl.IBrandService;
import com.shopme.common.entity.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> listAll() {
        return (List<Brand>) brandRepository.findAll();
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand get(Integer id) throws BrandNotFoundException {
        try {
            return brandRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BrandNotFoundException("Could not find any brand with ID" + id);
        }
    }

    @Override
    public void delete(Integer id) throws BrandNotFoundException {

        Long countById = brandRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new BrandNotFoundException("Could not find any brand with ID " + id);
        }

        brandRepository.deleteById(id);
    }
}
