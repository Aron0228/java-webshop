package com.example.javawebshop.controllers;

import com.example.javawebshop.dto.AddToCartRequest;
import com.example.javawebshop.entities.CartItem;
import com.example.javawebshop.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MainViewController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CarouselItemService carouselItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    /*@GetMapping("/")
    public String asd() {
        return "/new-design/test";
    }*/

    @GetMapping
    public String index(Model model, HttpServletRequest request, HttpSession session) {
        //userSessionService.setUserSpecificModelAttributes(model, request);
        //model.addAttribute("products", productService.getAll());
        return "new-design/test";
    }

    /*@PostMapping("/cart/add")
    @ResponseBody
    public String addCart(@RequestBody AddToCartRequest request, HttpSession session) {
        System.out.println("ASDASDASDASD");
        CartItem cartItem = cartItemService.createCartItem(request.getProductId());
        cartService.addToCart(session.getId(), cartItem);
        System.out.println("USERCART: " + cartService.getCart(session.getId()));
        return "Success";
    }*/
}
