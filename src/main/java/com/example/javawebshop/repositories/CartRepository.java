package com.example.javawebshop.repositories;

import com.example.javawebshop.entities.Cart;
import com.example.javawebshop.entities.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartRepository {
    private final Map<Long, List<CartItem>> carts = new HashMap<>();

    public void addCart(Long key) {
        if (!carts.containsKey(key)) {
            carts.put(key, new ArrayList<>());
        }
    }

    public void addToCart(Long userId, CartItem cartItem) {
        List<CartItem> cart = carts.getOrDefault(userId, new ArrayList<>());
        boolean found = false;
        System.out.println("DKFSHGPADSFOPGKFA" + cartItem.getQuantity());

        for (CartItem item : cart) {
            if (cartItem.getProductId().equals(item.getProductId())) {
                item.setQuantity(cartItem.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(cartItem);
        }

        carts.put(userId, cart);
    }

    public List<CartItem> getCart(Long userId) {
        return carts.getOrDefault(userId, new ArrayList<>());
    }
}
