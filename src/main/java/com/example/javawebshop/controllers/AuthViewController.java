package com.example.javawebshop.controllers;

import com.example.javawebshop.dto.LoginRequest;
import com.example.javawebshop.dto.LoginResponse;
import com.example.javawebshop.dto.RegistrationRequest;
import com.example.javawebshop.dto.RegistrationResponse;
import com.example.javawebshop.services.CartService;
import com.example.javawebshop.services.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;

@Controller
public class AuthViewController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserSessionService userSessionService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        System.out.println(session.getId());
        return "/new-design/login-page";
    }
    @PostMapping("/auth/login")
    public String loginPost(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse servletResponse,
            Model model,
            HttpSession session,
            HttpServletRequest servletRequest
    ) {
        List<Long> sessionCartRequests = (List<Long>) session.getAttribute("cartRequests");
        LoginRequest loginRequest = new LoginRequest(email, password);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);

        try {
            System.out.println("asdasd");
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                    "http://localhost:8081/api/auth/login",
                    requestEntity,
                    LoginResponse.class
            );

            List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
            if (cookies != null) {
                for (String cookie : cookies) {
                    servletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie);
                }
            }

            if (response.getStatusCode().is2xxSuccessful()) {
                cartService.handleLogin(Objects.requireNonNull(response.getBody()).getUserId(), sessionCartRequests);
            }

            return "redirect:/";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
            ResponseEntity<RegistrationResponse> response = restTemplate.postForEntity(
                    "http://localhost:8081/api/auth/register",
                    requestEntity,
                    RegistrationResponse.class
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
