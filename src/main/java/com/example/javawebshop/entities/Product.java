package com.example.javawebshop.entities;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "b64_image", columnDefinition = "TEXT")
    private String b64Image;
    private String name;
    private String description;
    @Column(name = "price", columnDefinition = "INTEGER")
    private Integer price;

    // --- Constructors ---
    public Product() {}
    public Product(Long id, String b64Image, String name, String description, Integer price) {
        this.id = id;
        this.b64Image = b64Image;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }
    public String getB64Image() {
        return b64Image;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Integer getPrice() {
        return price;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }
    public void setB64Image(String b64Image) {
        this.b64Image = b64Image;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
}
