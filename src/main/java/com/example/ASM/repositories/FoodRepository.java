package com.example.ASM.repositories;

import com.example.ASM.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {
}
