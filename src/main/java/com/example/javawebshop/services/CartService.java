package com.example.javawebshop.services;

import com.example.javawebshop.entities.Cart;
import com.example.javawebshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    public Cart getById(Long id) {
        return cartRepository.findById(id).get();
    }

    public Cart getByUserId(Long userId) {
        return cartRepository.getByUserId(userId).get();
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }
}
