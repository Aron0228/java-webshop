package com.example.javawebshop.services;

import com.example.javawebshop.entities.CartItem;
import com.example.javawebshop.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }
    public List<CartItem> getByCartId(Long cartId) {
        return cartItemRepository.getByCartId(cartId).orElse(Collections.emptyList());
    }

    public CartItem getById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
