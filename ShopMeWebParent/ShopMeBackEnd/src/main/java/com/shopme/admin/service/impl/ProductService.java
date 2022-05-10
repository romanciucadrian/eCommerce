package com.shopme.admin.service.impl;

import com.shopme.admin.repository.ProductRepository;
import com.shopme.common.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }
}
