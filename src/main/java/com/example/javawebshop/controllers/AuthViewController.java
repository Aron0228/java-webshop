package com.example.javawebshop.controllers;

import com.example.javawebshop.auth.AuthRequest;
import com.example.javawebshop.auth.RegistrationRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class AuthViewController {
    @GetMapping("/home")
    public String home() {
        return "auth/asd";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @PostMapping("/auth/login")
    public String loginPost(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse servletResponse,
            Model model
    ) {
        AuthRequest authRequest = new AuthRequest(email, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8081/api/auth/login",
                    requestEntity,
                    String.class
            );

            List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
            if (cookies != null) {
                for (String cookie : cookies) {
                    servletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie);
                }
            }

            return "redirect:/";
        }
        catch (Exception e) {
            model.addAttribute("error", "Sikertelen bejelentkezés");
            return "redirect:/login";
        }
    }
    @GetMapping("/registration")
    public String registration() {
        return "auth/registration";
    }
    @PostMapping("/auth/registration")
    public String registrationPost(
            @RequestParam String email,
            @RequestParam String fullName,
            @RequestParam String password,
            HttpServletResponse servletResponse,
            Model model
    ) {
        RegistrationRequest registrationRequest = new RegistrationRequest(email, fullName, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<RegistrationRequest> requestEntity = new HttpEntity<>(registrationRequest);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8081/api/auth/register",
                    requestEntity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new Exception("Something went wrong!");
            }

            return "redirect:/login";
        }
        catch (Exception e) {
            model.addAttribute("error", "Sikertelen regisztráció");
            return "redirect:/registration";
        }
    }
}
