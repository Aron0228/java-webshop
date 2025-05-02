package com.example.javawebshop.controllers;

import com.example.javawebshop.services.CarouselItemService;
import com.example.javawebshop.services.CategoryService;
import com.example.javawebshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainViewController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CarouselItemService carouselItemService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("products", productService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("carouselItems", carouselItemService.getAll());
        return "pages/main-view";
    }

    @PostMapping("/cart/add")
    public void addCart(@RequestParam("productId") Long productId) {

    }
}
