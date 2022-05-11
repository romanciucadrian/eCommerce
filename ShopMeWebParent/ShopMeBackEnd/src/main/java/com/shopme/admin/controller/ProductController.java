package com.shopme.admin.controller;

import java.io.IOException;
import java.util.List;

import com.shopme.admin.error.ProductNotFoundException;
import com.shopme.admin.service.BrandService;
import com.shopme.admin.service.ProductService;
import com.shopme.admin.util.FileUploadUtil;
import com.shopme.common.entity.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;


import com.shopme.common.entity.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;

    public ProductController(ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping("/products")
    public String listAll(Model model) {
        List<Product> listProducts = productService.listAll();

        model.addAttribute("listProducts", listProducts);

        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {

        List<Brand> listBrands = brandService.listAll();

        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", listBrands);
        model.addAttribute("pageTitle", "Create New Product");

        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product,
                              RedirectAttributes redirectAttributes,
                              @RequestParam("fileImage")MultipartFile mainImageMultipart,
                              @RequestParam("extraImage") MultipartFile[] extraImageMultiparts,
                              @RequestParam(name = "detailNames", required = false) String[] detailNames,
                              @RequestParam(name = "detailValues", required = false) String[] detailValues) throws IOException {

        setMainImageName(mainImageMultipart, product);

        setExtraImageNames(extraImageMultiparts, product);

        setProductDetails(detailNames, detailValues, product);

        Product savedProduct = productService.save(product);

        saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);

        redirectAttributes.addFlashAttribute("messageSuccess", "The product has been saved successfully.");

        return "redirect:/products";
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes) {

        productService.updateProductEnabledStatus(id, enabled);

        String status = enabled ? "enabled" : "disabled";
        String message = "The product ID " + id + " has been " + status;

        redirectAttributes.addFlashAttribute("messageSuccess", message);

        return  "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        try {
            productService.delete(id);

            String productExtraImagesDir = "../product-images/" + id + "/extras";
            String productImagesDir = "../product-images/" + id;

            FileUploadUtil.removeDir(productExtraImagesDir);
            FileUploadUtil.removeDir(productImagesDir);

            redirectAttributes.addFlashAttribute("messageSuccess", "The product ID " + id + " has been deleted!");
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("messageError", ex.getMessage());
        }
        return "redirect:/products";
    }

    private void setExtraImageNames(MultipartFile[] extraImageMultiparts, Product product) {

        if (extraImageMultiparts.length > 0) {

            for (MultipartFile multipartFile : extraImageMultiparts) {
                if (!multipartFile.isEmpty()) {

                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                    product.addExtraImage(fileName);
                }
            }
        }
    }

    private void setMainImageName(MultipartFile mainImageMultipart, Product product) {

        if (!mainImageMultipart.isEmpty()) {

            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());

            product.setMainImage(fileName);
        }
    }

    private void saveUploadedImages(MultipartFile mainImageMultipart,
                                    MultipartFile[] extraImageMultiparts, Product savedProduct) throws IOException {

        if (!mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());

            String uploadDir = "../product-images/" + savedProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);

            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
        }

        if (extraImageMultiparts.length > 0) {

            String uploadDir = "../product-images/" + savedProduct.getId() + "/extras";

            for (MultipartFile multipartFile : extraImageMultiparts) {

                if (multipartFile.isEmpty()) continue;

                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            }
        }
    }

    private void setProductDetails(String[] detailNames, String[] detailValues, Product product) {

        if (detailNames == null || detailNames.length == 0) return;

        for (int count = 0; count < detailNames.length; count++) {
            String name = detailNames[count];
            String value = detailValues[count];

            if (!name.isEmpty() && !value.isEmpty()) {
                product.addDetail(name, value);
            }
        }
    }
}