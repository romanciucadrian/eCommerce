package com.shopme.service.impl;

import com.shopme.common.entity.Product;
import com.shopme.common.error.ProductNotFoundException;
import org.springframework.data.domain.Page;

public interface IProductService {

    public Page<Product> listByCategory(int pageNum, Integer categoryId);

    public Product getProduct(String alias) throws ProductNotFoundException;

    public Page<Product> search(String keyword, int pageNum);

}
