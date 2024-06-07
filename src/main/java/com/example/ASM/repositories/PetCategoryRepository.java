package com.example.ASM.repositories;

import com.example.ASM.models.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, UUID> {
    Optional<PetCategory> getPetCategoryByName(String s);

}
