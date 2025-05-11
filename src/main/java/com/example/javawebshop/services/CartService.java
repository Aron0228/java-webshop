package com.example.javawebshop.services;

import com.example.javawebshop.dto.AddToCartRequest;
import com.example.javawebshop.entities.CartItem;
import com.example.javawebshop.repositories.CartRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostConstruct
    public void initCarts() {
        List<CartItem> cartItems = cartItemService.getAll();
        for (CartItem cartItem : cartItems) {
            cartRepository.addCart(cartItem.getUserId());
            cartRepository.addToCart(cartItem.getUserId(), cartItem);
        }
    }

    public void handleLogin(Long userId, List<Long> sessionCart) {
        if (getDatabaseCartSize(userId) == 0) {
            List<Long> unique = sessionCart.stream()
                    .distinct()
                    .collect(Collectors.toList());

            Map<Long, Integer> frequencyMap = new HashMap<>();
            for (Long value : sessionCart) {
                frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
            }

            for(Long id : unique) {
                int count = frequencyMap.get(id);
                System.out.println("SDFJIGHIASDFKOSPADGNPEDRFIHGEORGJSODFKSDOSADFIJG" + id + " " + count);
                addToDatabaseCart(userId, count, id);
            }
        }
    }

    public int getCartSize(
            Long userId,
            HttpSession session
    ) {
        if (userId == null) {
            List<Long> sessionCartRequests = (List<Long>) session.getAttribute("cartRequests");

            if (sessionCartRequests == null) {
                return 0;
            }
            return sessionCartRequests.size();
        }
        else {
            return getDatabaseCartSize(userId);
        }
    }

    private int getDatabaseCartSize(Long userId) {
        List<CartItem> userCart = cartRepository.getCart(userId);
        int cartSize = 0;
        for (CartItem cartItem : userCart) {
            cartSize += cartItem.getQuantity();
        }
        System.out.println(cartSize);
        return cartSize;
    }

    public void addToCart(
            Long userId,
            Long productId,
            Integer quantity,
            HttpSession session
    ) {
        if (userId == null) {
            addToSessionCart(productId, quantity, session);
        }
        else {
            addToDatabaseCart(userId, quantity, productId);
        }
    }

    private void addToSessionCart(Long productId, Integer quantity, HttpSession session) {
        List<Long> sessionCartRequests = (List<Long>) session.getAttribute("cartRequests");
        if (sessionCartRequests == null) {
            sessionCartRequests = new ArrayList<>();
        }
        for (int i = 0; i < quantity; i++){
            sessionCartRequests.add(productId);
        }
        session.setAttribute("cartRequests", sessionCartRequests);
    }

    private void addToDatabaseCart(Long userId, Integer quantity, Long productId) {
        CartItem cartItem = cartItemService.createCartItem(userId, quantity, productId);
        cartRepository.addToCart(userId, cartItem);
    }

    /*public boolean hasCart(String key) {
        return cartRepository.hasCart(key);
    }

    public void addToCart(String key, CartItem cartItem) {
        cartRepository.addToCart(key, cartItem);
    }

    public void addCart(String key) {
        cartRepository.addCart(key);
    }

    public List<CartItem> getCart(String key) {
        return cartRepository.getCart(key);
    }*/
}
