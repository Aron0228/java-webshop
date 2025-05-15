package com.example.javawebshop.services;

import com.example.javawebshop.entities.CarouselItem;
import com.example.javawebshop.entities.Product;
import com.example.javawebshop.repositories.CarouselItemRepository;
import com.example.javawebshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarouselItemService {
    private CarouselItemRepository carouselItemRepository;
    private ProductRepository productRepository;

    @Autowired
    public CarouselItemService(CarouselItemRepository carouselItemRepository, ProductRepository productRepository) {
        this.carouselItemRepository = carouselItemRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        for (CarouselItem item : carouselItemRepository.findAll()) {
            products.add(productRepository.findById(item.getItemId()).orElse(null));
        }

        return products;
    }

    public void setCarouselItemsInModel(Model model) {
        List<CarouselItem> carouselItems = carouselItemRepository.findAll();
        List<Product> products = new ArrayList<>();

        for (CarouselItem item : carouselItems) {
            products.add(productRepository.findById(item.getItemId()).orElse(null));
        }

        model.addAttribute("carouselItems", products);
    }
}
