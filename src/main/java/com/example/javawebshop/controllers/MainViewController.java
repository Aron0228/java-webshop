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
        System.out.println("USERID: " + userSessionService.getCurrentUserId(request));
        System.out.println("USERROLE: " + userSessionService.getCurrentUserRole(request));
        System.out.println("USERFULLNAME: " + userSessionService.getCurrentUserFullName(request));
        /*if (userSessionService.getCurrentUserFullName(request) != null) {
            model.addAttribute("welcomeText",
                    "Welcome, " + userSessionService.getCurrentUserFullName(request).split(" ")[1] + "!");
        }
        else {
            model.addAttribute("welcomeText", "Welcome!");
        }*/

        userSessionService.setUserSpecificModelAttributes(model, request);

        System.out.println("HTTPSESSIONID: " + session.getId());
        model.addAttribute("products", productService.getAll());
        /*model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("carouselItems", carouselItemService.getAll());*/
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
