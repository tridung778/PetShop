package com.example.ASM.services;

import com.example.ASM.models.Pet;
import com.example.ASM.models.PetCategory;
import com.example.ASM.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Override
    public void savePet(Pet pet) {
        petRepository.save(pet);
    }

    @Override
    public PetCategory getPetCategoryByName(String s) {
        return petRepository.getPetCategoryByName(s);
    }

    @Override
    public List<Pet> findAllPet() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> findAllPet(Sort sort) {
        return petRepository.findAll(sort);
    }

    @Override
    public List<Pet> findAllPetByCategory(String category) {
        return petRepository.getPetsByCategoryName(category);
    }

    public Page<Pet> findAllPetByCategory(String categoryName, Pageable pageable) {
        return petRepository.findByCategoryName(categoryName, pageable);
    }

    @Override
    public List<String> findAllBreedsByCategoryName(String category) {
        return petRepository.getAllBreedsByCategoryName(category);
    }

    @Override
    public List<Pet> getPetsByCategoryNameAndBreedInAndPriceBetween(String categoryName, List<String> breeds, double minPrice, double maxPrice, Sort sort) {
        return petRepository.findByCategoryNameAndBreedInAndPriceBetween(categoryName, breeds, minPrice, maxPrice, sort);
    }

    @Override
    public Optional<Pet> findById(UUID id) {
        return petRepository.findById(id);
    }

    @Override
    public Pet findPetById(UUID id) {
        return petRepository.findPetById(id);
    }

    @Override
    public Pet findPetByName(String nameProduct) {
        return petRepository.findPetByName(nameProduct);
    }

    @Override
    public String findCategoryNameByPetId(UUID id) {
        return petRepository.findCategoryNameByPetId(id);
    }

    @Override
    public void deleteById(UUID id) {
        petRepository.deleteById(id);
    }

    @Override
    public Pet findByNameContainingIgnoreCase(String name) {
        return petRepository.findByNameContainingIgnoreCase(name);
    }

}
