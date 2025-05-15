package com.example.javawebshop.controllers;

import com.example.javawebshop.dto.AddToCartRequest;
import com.example.javawebshop.entities.Cart;
import com.example.javawebshop.entities.CartItem;
import com.example.javawebshop.entities.CartItemViewEntity;
import com.example.javawebshop.services.CartItemService;
import com.example.javawebshop.services.CartService;
import com.example.javawebshop.services.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("")
    public String myCart(HttpServletRequest request, HttpSession session, Model model) {
        userSessionService.setUserSpecificModelAttributes(model, request);

        List<CartItemViewEntity> cartItemViewEntities = cartItemService.getCartItemViews(session, userSessionService.getCurrentUserId(request));

        model.addAttribute("cartItemViewEntities", cartItemViewEntities);

        return "new-design/my-cart-page";
    }

    @PostMapping("/change-quantity")
    @ResponseBody
    public void changeQuantity(HttpServletRequest request, HttpSession session, Model model) {
        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        Long userId = userSessionService.getCurrentUserId(request);

        cartService.setQuantityOfCartItem(
                userSessionService.getCurrentUserId(request),
                productId,
                quantity,
                session);
    }

    @GetMapping("/size")
    @ResponseBody
    public int getCartSize(HttpServletRequest request, HttpSession session) {
        return cartService.getCartSize(
                userSessionService.getCurrentUserId(request),
                session
        );
    }

    @PostMapping("/add")
    @ResponseBody
    public void addToCart(@RequestBody AddToCartRequest addToCartRequest, HttpServletRequest request, HttpSession session) {
        cartService.addToCart(
                userSessionService.getCurrentUserId(request),
                addToCartRequest.getProductId(),
                addToCartRequest.getQuantity(),
                session
        );
    }
}
