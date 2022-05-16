package com.shopme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;
import com.shopme.repository.CategoryRepository;
import com.shopme.service.impl.ICategoryService;

@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listNoChildrenCategories() {

        List<Category> listNoChildrenCategories = new ArrayList<>();

        List<Category> listEnabledCategories = categoryRepository.findAllEnabled();

        listEnabledCategories.forEach(category -> {
            Set<Category> children = category.getChildren();
            if (children == null || children.size() == 0) {
                listNoChildrenCategories.add(category);
            }
        });

        return listNoChildrenCategories;
    }

}