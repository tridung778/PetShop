package com.example.ASM.repositories;

import com.example.ASM.models.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {
}
