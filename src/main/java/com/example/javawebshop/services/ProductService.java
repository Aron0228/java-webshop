package com.example.javawebshop.services;

import com.example.javawebshop.entities.Category;
import com.example.javawebshop.entities.Product;
import com.example.javawebshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getAll(Pageable pageable, String filter, Long categoryId) {
        if (categoryId != null) {
            // Ha van kategória, akkor szűrünk a kategória alapján
            if (filter == null || filter.isBlank()) {
                return productRepository.findByCategoriesId(categoryId, pageable);
            } else {
                return productRepository.findByCategoriesIdAndNameContainingIgnoreCase(categoryId, filter, pageable);
            }
        } else {
            // Ha nincs kategória, akkor a szűrés név alapján történik
            if (filter == null || filter.isBlank()) {
                System.out.println("asd");
                return productRepository.findAll(pageable);
            } else {
                return productRepository.findByNameContainingIgnoreCase(filter, pageable);
            }
        }
    }


    public Set<Category> getCategoriesByProductId(Long productId) {
        return productRepository.findCategoriesByProductId(productId);
    }

    public Product updateCategories(Long productId, Set<Category> categories) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setCategories(categories);
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }
}
