package com.shopme.service.impl;

import com.shopme.common.entity.Product;
import org.springframework.data.domain.Page;

public interface IProductService {

    public Page<Product> listByCategory(int pageNum, Integer categoryId);

}
