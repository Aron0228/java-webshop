package com.example.javawebshop.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String fullName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    // --- Constructors ---
    public User() {}
    public User(String email, String fullName, String password, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
    public String getLastName() {
        return fullName.split(" ")[1];
    }
    public String getPassword() {
        return password;
    }
    public Role getRole() {
        return role;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
