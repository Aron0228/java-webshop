package com.example.javawebshop.repositories;

import com.example.javawebshop.entities.Category;
import com.example.javawebshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category WHERE parent_id IS NULL", nativeQuery = true)
    List<Category> findParents();

    @Query(value = "SELECT * FROM category WHERE parent_id IS NOT NULL", nativeQuery = true)
    List<Category> findAllChildren();

    @Query(value = "SELECT * FROM category WHERE parent_id = :parentId", nativeQuery = true)
    List<Category> findAllChildrenByParentId(@Param("parentId") Long parentId);

    @Query("SELECT c.products FROM Category c WHERE c.id = :categoryId")
    Set<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);
}
