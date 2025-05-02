package com.example.javawebshop.services;

import com.example.javawebshop.entities.Category;
import com.example.javawebshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
