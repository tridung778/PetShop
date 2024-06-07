package com.example.ASM.services;

import com.example.ASM.models.PetCategory;
import com.example.ASM.repositories.PetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PetCategoryServiceImpl implements PetCategoryService {

    @Autowired
    PetCategoryRepository petCategoryRepository;

    @Override
    public void savePetCategory(PetCategory petCategory) {
        petCategoryRepository.save(petCategory);
    }

    @Override
    public Optional<PetCategory> getPetCategoryByName(String s) {
        return petCategoryRepository.getPetCategoryByName(s);
    }


}
