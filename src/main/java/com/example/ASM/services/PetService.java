package com.example.ASM.services;

import com.example.ASM.models.Pet;
import com.example.ASM.models.PetCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetService {

    void savePet(Pet pet);

    PetCategory getPetCategoryByName(String s);

    List<Pet> findAllPet();

    List<Pet> findAllPet(Sort sort);
    Page<Pet> findAllPetByCategory(String petCategory, Pageable pageable);

    List<Pet> findAllPetByCategory(String petCategory);

    List<String> findAllBreedsByCategoryName(String petCategory);

    List<Pet> getPetsByCategoryNameAndBreedInAndPriceBetween(String categoryName, List<String> breeds, double minPrice, double maxPrice, Sort sort);

    Optional<Pet> findById(UUID id);

    Pet findPetById(UUID id);

    Pet findPetByName(String nameProduct);

   String findCategoryNameByPetId(UUID id);

    void deleteById(UUID id);

    Pet findByNameContainingIgnoreCase(String name);
}
