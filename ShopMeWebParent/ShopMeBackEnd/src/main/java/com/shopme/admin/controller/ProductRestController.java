package com.shopme.admin.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.service.ProductService;

@RestController
public class ProductRestController {

    private final ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/products/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name) {
        return service.checkUnique(id, name);
    }
}