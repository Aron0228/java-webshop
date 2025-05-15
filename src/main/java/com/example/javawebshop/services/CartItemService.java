package com.example.javawebshop.services;

import com.example.javawebshop.entities.CartItem;
import com.example.javawebshop.entities.CartItemViewEntity;
import com.example.javawebshop.entities.Product;
import com.example.javawebshop.repositories.CartItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

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

    public List<CartItemViewEntity> getCartItemViews(HttpSession session, Long userId) {
        List<CartItemViewEntity> cartItemViews = new ArrayList<>();

        if (userId != null) {
            List<CartItem> cartItems = getByUserId(userId);
            System.out.println("GETCARTITEMVIEWS" + cartItems);
            for (CartItem cartItem : cartItems) {
                Product product = productService.getById(cartItem.getProductId()).orElse(null);

                assert product != null;
                cartItemViews.add(
                        new CartItemViewEntity(
                                product.getB64Image(),
                                product.getName(),
                                product.getPackaging(),
                                cartItem.getQuantity(),
                                product.getPrice() * cartItem.getQuantity(),
                                product.getId()));
            }

            return cartItemViews;
        }
        else {
            List<Long> sessionCartRequests = (List<Long>) session.getAttribute("cartRequests");

            if (sessionCartRequests == null) {
                return Collections.emptyList();
            }

            List<Long> unique = sessionCartRequests.stream()
                    .distinct()
                    .collect(Collectors.toList());

            Map<Long, Integer> frequencyMap = new HashMap<>();
            for (Long value : sessionCartRequests) {
                frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
            }

            for (Long productId : unique) {
                Product product = productService.getById(productId).orElse(null);

                cartItemViews.add(new CartItemViewEntity(
                        product.getB64Image(),
                        product.getName(),
                        product.getPackaging(),
                        frequencyMap.get(productId),
                        product.getPrice() * frequencyMap.get(productId),
                        product.getId()
                ));
            }

            return cartItemViews;
        }
    }
}
