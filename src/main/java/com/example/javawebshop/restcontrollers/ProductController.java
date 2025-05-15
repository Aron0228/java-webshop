package com.example.javawebshop.restcontrollers;

import com.example.javawebshop.entities.Product;
import com.example.javawebshop.entities.SortOption;
import com.example.javawebshop.repositories.ProductRepository;
import com.example.javawebshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/api/products")
    @ResponseBody
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "9") int size,
                                     @RequestParam(defaultValue = "NONE") SortOption sortOption,
                                     @RequestParam(defaultValue = "") String filter,
                                     @RequestParam(defaultValue = "null") String categoryId) {

        Sort sort = Sort.unsorted();
        if (sortOption == SortOption.ASCENDING) {
            sort = Sort.by(Sort.Order.asc("price"));
        } else if (sortOption == SortOption.DESCENDING) {
            sort = Sort.by(Sort.Order.desc("price"));
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Long categoryIdParsed = categoryId != null && !categoryId.isEmpty() && !"null".equals(categoryId) ? Long.parseLong(categoryId) : null;

        return productService.getAll(pageable, filter, categoryIdParsed);  // Új paraméter átadása
    }
}
