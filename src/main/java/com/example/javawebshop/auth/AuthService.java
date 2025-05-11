package com.example.javawebshop.auth;

import com.example.javawebshop.dto.LoginRequest;
import com.example.javawebshop.dto.LoginResponse;
import com.example.javawebshop.dto.RegistrationRequest;
import com.example.javawebshop.dto.RegistrationResponse;
import com.example.javawebshop.entities.Role;
import com.example.javawebshop.entities.User;
import com.example.javawebshop.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, HttpServletResponse response) {
        if (!checkLoginEmail(loginRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "User not found"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user);

            String cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(60)
                    .sameSite("None")
                    .build()
                    .toString();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie);

            return ResponseEntity.ok(new LoginResponse(user.getId(), null));

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, e.getMessage()));
        }
    }

    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
        if (!checkRegistrationEmail(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RegistrationResponse("Email already in use"));
        }
        if (!checkFullName(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RegistrationResponse("Name is wrong"));
        }
        if (!checkRegistrationPassword(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RegistrationResponse("Password is wrong"));
        }

        User user = new User(
                request.getEmail(),
                request.getFullName(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );
        userRepository.save(user);

        return ResponseEntity.ok(new RegistrationResponse("ok"));
    }

    public String checkLoginCredentials(LoginRequest loginRequest) {
        return checkLoginEmail(loginRequest) ? "OK" : "User not found";
    }

    public String checkRegistrationCredentials(RegistrationRequest registrationRequest) {
        if (!checkRegistrationEmail(registrationRequest)) {
            return "Email already in use";
        }
        if (!checkFullName(registrationRequest)) {
            return "Name is wrong";
        }
        if (!checkRegistrationPassword(registrationRequest)) {
            return "Password is wrong";
        }

        return "OK";
    }

    private boolean checkLoginEmail(LoginRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return passwordEncoder.matches(request.getPassword(), user.getPassword());
        }

        return false;
    }

    private boolean checkRegistrationEmail(RegistrationRequest request) {
        return !userRepository.existsByEmail(request.getEmail());
    }

    private boolean checkRegistrationPassword(RegistrationRequest request) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

        return request.getPassword().matches(regex) && request.getPassword().length() >= 8;
    }

    private boolean checkFullName(RegistrationRequest request) {
        String regex = "^[A-Z][a-zA-Z]* [A-Z][a-zA-Z]*$";

        return request.getFullName().matches(regex);
    }
}
