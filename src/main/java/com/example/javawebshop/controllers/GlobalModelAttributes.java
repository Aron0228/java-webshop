package com.example.javawebshop.controllers;

import com.example.javawebshop.services.CarouselItemService;
import com.example.javawebshop.services.CategoryService;
import com.example.javawebshop.services.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private CarouselItemService carouselItemService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void addCarouselItemsToModel(Model model) {
        carouselItemService.setCarouselItemsInModel(model);
    }

    @ModelAttribute
    public void addUserSessionToModel(Model model, HttpServletRequest request) {
        userSessionService.setUserSpecificModelAttributes(model, request);
    }

    @ModelAttribute
    public void addCategoryToModel(Model model, HttpServletRequest request) {
        categoryService.addCategoriesToModel(model);
    }
}
