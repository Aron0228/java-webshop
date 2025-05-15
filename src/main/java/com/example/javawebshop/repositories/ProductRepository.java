package com.example.javawebshop.repositories;

import com.example.javawebshop.entities.Category;
import com.example.javawebshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoriesId(Long categoryId, Pageable pageable);

    Page<Product> findByCategoriesIdAndNameContainingIgnoreCase(Long categoryId, String name, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    @Query("SELECT p.categories FROM Product p WHERE p.id = :productId")
    Set<Category> findCategoriesByProductId(@Param("productId") Long productId);
}
