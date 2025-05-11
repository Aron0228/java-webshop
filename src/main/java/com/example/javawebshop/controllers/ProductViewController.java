package com.example.javawebshop.controllers;

import com.example.javawebshop.entities.Product;
import com.example.javawebshop.services.ProductService;
import com.example.javawebshop.services.UserService;
import com.example.javawebshop.services.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductViewController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserSessionService userSessionService;

    @GetMapping("/products/{id}")
    public String productView(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product product = productService.getById(id).orElseThrow();

        userSessionService.setUserSpecificModelAttributes(model, request);

        model.addAttribute("product", product);
        return "/new-design/product-dynamic-linked-page";
    }

}
