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
    private String card;
    private Integer packaging;
    private String mark;

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
    public String getCard() {
        return card;
    }
    public Integer getPackaging() {
        return packaging;
    }
    public String getMark() {
        return mark;
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
    public void setCard(String card) {
        this.card = card;
    }
    public void setPackaging(Integer packaging) {
        this.packaging = packaging;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
}
