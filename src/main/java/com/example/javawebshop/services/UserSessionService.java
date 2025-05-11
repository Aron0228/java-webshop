package com.example.javawebshop.services;

import com.example.javawebshop.auth.JwtService;
import com.example.javawebshop.entities.ProfileAction;
import com.example.javawebshop.entities.Role;
import com.example.javawebshop.entities.User;
import com.example.javawebshop.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserSessionService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserSessionService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public Long getCurrentUserId(HttpServletRequest request) {
        String token = extractTokenFromCookie(request);
        if (token != null) {
            return jwtService.extractUserId(token).orElse(null);
        }
        return null;
    }

    public String getCurrentUserFullName(HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        if (userId == null) {
            return null;
        }

        User user = userRepository.findById(userId).orElse(null);

        return user.getFullName() != null ? user.getFullName() : null;
    }

    public Role getCurrentUserRole(HttpServletRequest request) {
        String token = extractTokenFromCookie(request);
        if (token != null) {
            return jwtService.extractRole(token).orElse(null);
        }
        return null;
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public void setUserSpecificModelAttributes(Model model, HttpServletRequest request) {
        List<ProfileAction> actions = new ArrayList<>();
        if (getCurrentUserFullName(request) != null) {
            model.addAttribute("welcomeText",
                    "Welcome, " + getCurrentUserFullName(request).split(" ")[1] + "!");

            if (getCurrentUserRole(request) == Role.USER) {
                actions.add(new ProfileAction("My Orders", "asd"));
                actions.add(new ProfileAction("Logout", "asd"));
            }
            else if (getCurrentUserRole(request) == Role.ADMIN) {
                actions.add(new ProfileAction("Admin Console", "asd"));
                actions.add(new ProfileAction("Logout", "asd"));
            }
        }
        else {
            actions.add(new ProfileAction("Login", "/login"));
            actions.add(new ProfileAction("Registration", "/registration"));
            model.addAttribute("welcomeText", "Welcome!");
        }

        model.addAttribute("profileActions", actions);
    }
}
