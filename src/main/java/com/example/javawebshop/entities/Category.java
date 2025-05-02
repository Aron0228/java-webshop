package com.example.javawebshop.entities;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "fa_class", columnDefinition = "TEXT")
    private String faClass;

    public Category() {}
    public Category(String name, String faClass) {
        this.name = name;
        this.faClass = faClass;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFaClass() {
        return faClass;
    }
    public void setFaClass(String faClass) {
        this.faClass = faClass;
    }
}
