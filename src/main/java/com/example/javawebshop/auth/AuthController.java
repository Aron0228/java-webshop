package com.example.javawebshop.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        return authService.login(request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        return authService.logout(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login/check-credentials")
    public ResponseEntity<?> checkLoginCredentials(@RequestBody AuthRequest request) {
        try {
            boolean valid = authService.checkLoginCredentials(request);
            if (valid) {
                return ResponseEntity.ok().body("valid");
            } else {
                return ResponseEntity.status(401).body("invalid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("error");
        }
    }

    @PostMapping("/register/check-credentials")
    public ResponseEntity<?> checkRegistrationCredentials(@RequestBody RegistrationRequest request) {
        try {
            boolean valid = authService.checkRegistrationCredentials(request);
            boolean passwordValid = authService.checkRegistrationPassword(request);
            boolean nameValid = authService.checkFullName(request);
            if (valid && passwordValid && nameValid) {
                return ResponseEntity.ok().body("valid");
            }
            else if (!passwordValid) {
                return ResponseEntity.status(401).body("invalid password");
            }
            else if (!nameValid) {
                return ResponseEntity.status(401).body("invalid name");
            }
            return ResponseEntity.status(401).body("invalid email");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("error");
        }
    }
}
