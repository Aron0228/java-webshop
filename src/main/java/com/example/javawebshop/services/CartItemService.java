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
    public List<CartItem> getByUserId(Long userId) {
        return cartItemRepository.getByUserId(userId).orElse(Collections.emptyList());
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

    public CartItem createCartItem(Long userId, Integer quantity, Long productId) {
        List<CartItem> userCartItems = getByUserId(userId);

        for (CartItem cartItem : userCartItems) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return cartItemRepository.save(cartItem);
            }
        }

        CartItem cartItem = new CartItem(userId, productId, quantity);
        return cartItemRepository.save(cartItem);
    }
}
