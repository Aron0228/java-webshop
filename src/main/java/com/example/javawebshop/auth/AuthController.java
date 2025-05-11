package com.example.javawebshop.auth;

import com.example.javawebshop.dto.LoginRequest;
import com.example.javawebshop.dto.LoginResponse;
import com.example.javawebshop.dto.RegistrationRequest;
import com.example.javawebshop.dto.RegistrationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.login(request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        return authService.logout(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login/check-credentials")
    public String checkLoginCredentials(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        return authService.checkLoginCredentials(request);
    }

    @GetMapping("/register/check-credentials")
    public String checkRegistrationCredentials(
            @RequestBody RegistrationRequest request,
            HttpServletResponse response
    ) {
        return authService.checkRegistrationCredentials(request);
    }
}
