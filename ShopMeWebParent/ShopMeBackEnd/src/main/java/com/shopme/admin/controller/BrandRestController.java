package com.shopme.admin.controller;

import com.shopme.admin.error.BrandNotFoundException;
import com.shopme.admin.error.BrandNotFoundRestException;
import com.shopme.admin.service.BrandService;
import com.shopme.admin.util.CategoryDTO;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BrandRestController {

    private final BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/brands/check_unique")
    public String checkDuplicateEmail(@Param("id") Integer id,
                                      @Param("name") String name) {

        return brandService.checkUnique(id, name);
    }

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundRestException {

        List<CategoryDTO> listCategories = new ArrayList<>();

        try {
            Brand brand = brandService.get(brandId);
            Set<Category> categories = brand.getCategories();

            for (Category category : categories) {

                CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName());
                listCategories.add(categoryDTO);
            }

            return listCategories;

        } catch (BrandNotFoundException e) {
            throw new BrandNotFoundRestException();
        }
    }
}
