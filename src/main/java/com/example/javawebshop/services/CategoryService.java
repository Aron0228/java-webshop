package com.example.javawebshop.services;

import com.example.javawebshop.entities.Category;
import com.example.javawebshop.entities.Product;
import com.example.javawebshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getParentCategories() {
        return categoryRepository.findParents();
    }

    public List<Category> getAllChildrenCategories() {
        return categoryRepository.findAllChildren();
    }

    public List<Category> getChildrenCategories(Long parentId) {
        return categoryRepository.findAllChildrenByParentId(parentId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategoriesToModel(Model model) {
        List<Category> parentCategories = getParentCategories();
        Map<Category, List<Category>> categoryMap = new LinkedHashMap<>();

        for (Category parent : parentCategories) {
            List<Category> children = getChildrenCategories(parent.getId());
            categoryMap.put(parent, children);
        }

        model.addAttribute("categoryMap", categoryMap);
    }

    public Set<Product> getProductsByCategoryId(Long categoryId) {
        return categoryRepository.findProductsByCategoryId(categoryId);
    }
}

