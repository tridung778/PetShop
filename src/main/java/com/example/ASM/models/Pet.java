package com.example.ASM.models;

import com.example.ASM.enums.Gender;
import com.example.ASM.enums.PetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String breed;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PetCategory category;

    @Enumerated(EnumType.STRING)
    private PetStatus status;

}

