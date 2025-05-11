package com.example.javawebshop.entities;

import java.util.List;

public class Cart {
    private List<CartItem> cartItems;
    private String key;
    private int total;

    public Cart() {}
    public Cart(List<CartItem> cartItems, String key, int total) {
        this.cartItems = cartItems;
        this.key = key;
        this.total = total;
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }
    public String getKey() {
        return key;
    }
}
