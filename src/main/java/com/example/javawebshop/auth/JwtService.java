package com.example.javawebshop.auth;

import com.example.javawebshop.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private final String SECRET = "egy_sokkal_hosszabb_titkos_jelszo_pl_legalabb_32_karakter!";

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Optional<Long> extractUserId(String token) {
        try {
            String subject = extractClaims(token).getSubject();
            return Optional.of(Long.parseLong(subject));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        return extractUserId(token)
                .map(id -> id.equals(userDetails.getUserId()))
                .orElse(false);
    }

}