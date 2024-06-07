package com.example.ASM.ultis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PetFilterForm {

    private List<String> breeds;
    private List<String> selectedBreeds;
    private double range1;
    private double range2;

    public PetFilterForm() {
        selectedBreeds = new ArrayList<>();
    }
}
