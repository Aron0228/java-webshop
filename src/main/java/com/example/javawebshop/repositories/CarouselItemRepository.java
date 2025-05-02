package com.example.javawebshop.repositories;

import com.example.javawebshop.entities.CarouselItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselItemRepository extends JpaRepository<CarouselItem, Long> {
}
