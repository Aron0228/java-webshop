package com.example.javawebshop.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "\"carousel_item\"")
public class CarouselItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long itemId;

    public CarouselItem() {}
    public CarouselItem(Long itemId) {
        this.itemId = itemId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
