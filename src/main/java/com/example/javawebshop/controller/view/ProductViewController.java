package com.example.javawebshop.controller.view;

import com.example.javawebshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductViewController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getProducts(Model model) {
        System.out.println("asd");
        model.addAttribute("products", productService.getAll());
        return "products/list.html"; // -> src/main/resources/templates/products/list.html
    }
}


