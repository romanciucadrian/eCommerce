package com.shopme.admin.service;

import com.shopme.admin.error.ProductNotFoundException;
import com.shopme.admin.repository.ProductRepository;
import com.shopme.admin.service.impl.IProductService;
import com.shopme.common.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll("", "-"));
        }
        product.setUpdatedTime(new Date());

        return productRepository.save(product);
    }

    @Override
    public String checkUnique(Integer id, String name) {

        boolean isCreatingNew = (id == null || id == 0);

        Product productByName = productRepository.findByName(name);

        if (isCreatingNew) {
            if (productByName != null) return "Duplicate";
        } else {
            if (productByName != null && productByName.getId() != id) {
                return "Duplicate";
            }
        }
        return "OK";
    }

    @Override
    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        productRepository.updateEnabledStatus(id, enabled);
    }

    @Override
    public void delete(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product get(Integer id) throws ProductNotFoundException {
        try {
            return productRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Could not find any product with ID " + id);
        }
    }
}
