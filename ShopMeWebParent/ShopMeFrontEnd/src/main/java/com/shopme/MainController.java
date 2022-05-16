package com.shopme;

import java.util.List;

import com.shopme.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.common.entity.Category;

@Controller
public class MainController {

  private final CategoryService categoryService;

    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String viewHomePage(Model model) {
        List<Category> listCategories = categoryService.listNoChildrenCategories();
        model.addAttribute("listCategories", listCategories);

        return "index";
    }
}