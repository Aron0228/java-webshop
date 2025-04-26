package com.example.javawebshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {
    @GetMapping("/")
    public String home() {
        return "auth/asd";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/registration")
    public String registration() {
        return "auth/registration";
    }
}
