package com.example.ASM.repositories;

import com.example.ASM.models.Pet;
import com.example.ASM.models.PetCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    PetCategory getPetCategoryByName(String s);

    List<Pet> getPetsByCategoryName(String name);

    Page<Pet> findByCategoryName(String name, Pageable pageable);

    @Query("SELECT DISTINCT p.breed FROM Pet p WHERE p.category.name = :categoryName")
    List<String> getAllBreedsByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Pet p WHERE p.category.name = :categoryName AND p.breed IN :breeds AND p.price BETWEEN :minPrice AND :maxPrice")
    List<Pet> findByCategoryNameAndBreedInAndPriceBetween(String categoryName, List<String> breeds, double minPrice, double maxPrice, Sort sort);

    Pet findPetById(UUID id);

    Pet findPetByName(String name);

    @Query("SELECT p.category.name FROM Pet p WHERE p.id = :petId")
    String findCategoryNameByPetId(@Param("petId") UUID petId);

    Pet findByNameContainingIgnoreCase(String name);

}
