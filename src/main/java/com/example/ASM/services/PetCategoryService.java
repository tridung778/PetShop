package com.example.ASM.services;

import com.example.ASM.models.PetCategory;

import java.util.Optional;
import java.util.UUID;

public interface PetCategoryService {
    void savePetCategory(PetCategory petCategory);

    Optional<PetCategory> getPetCategoryByName(String s);

}
