package com.example.ASM.repositories;

import com.example.ASM.models.Toy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ToyRepository extends JpaRepository<Toy, UUID> {
}
