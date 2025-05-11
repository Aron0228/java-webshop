package com.example.javawebshop.repositories;

import com.example.javawebshop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<List<CartItem>> getByUserId(Long userId);
}
