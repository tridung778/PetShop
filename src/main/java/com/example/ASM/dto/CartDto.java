package com.example.ASM.dto;

import com.example.ASM.models.BaseEntity;
import com.example.ASM.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cartDto")
public class CartDto extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String thumbnail;
    private String name;
    private String type;
    private String specie;
    private double price;
    @Max(100)
    @Min(0)
    private int quantity;
}
