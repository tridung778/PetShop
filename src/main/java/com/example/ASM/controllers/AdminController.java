package com.example.ASM.controllers;

import com.example.ASM.models.Pet;
import com.example.ASM.models.PetCategory;
import com.example.ASM.services.PetCategoryService;
import com.example.ASM.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final String path = "C:\\Java 5\\ASM\\src\\main\\resources\\static\\images\\";

    @Autowired
    private PetService petService;

    @Autowired
    private PetCategoryService petCategoryService;

    @RequestMapping("")
    public String indexAdmin(Model model) {
        model.addAttribute("page", "admin-dashboard.jsp");
        return "admin-page";
    }

    @GetMapping("/add-product")
    public String addProducts(Model model, @ModelAttribute("pet") Pet pet) {
        model.addAttribute("pets", petService.findAllPet());
        model.addAttribute("page", "admin-add-products.jsp");
        return "admin-page";
    }

    @RequestMapping("/add-product/save")
    public String save(@ModelAttribute("pet") Pet newPet,
                       Model model, @RequestParam("photo-file") MultipartFile photoFile) throws IOException {


        String petCategoryName = newPet.getCategory().getName();
        Pet pet = new Pet();

        Optional<PetCategory> petCategory = petCategoryService.getPetCategoryByName(petCategoryName);
        petCategory.ifPresent(newPet::setCategory);
        newPet.setId(UUID.randomUUID());

        String fileName = null;
        if (!photoFile.isEmpty()) {
            fileName = photoFile.getOriginalFilename();
            String filePath = path + File.separator + fileName;

            File parentDir = new File(path);
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            photoFile.transferTo(new File(filePath));
        }
        if (newPet.getImage() != null) {
            pet.setImage(pet.getImage());
        } else {
            pet.setImage(fileName);
        }

        petService.savePet(newPet);
        return "redirect:/admin/add-product";
    }


    @RequestMapping("/add-product/remove/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        System.out.println("ddd");
        petService.deleteById(id);
        return "redirect:/admin/add-product";
    }

    @GetMapping("/add-product/edit/{id}")
    public String editProduct(@PathVariable("id") UUID id, Model model, @ModelAttribute("pet") Pet pet) {
        Pet existingProduct = petService.findById(id).orElse(null);
        if (existingProduct != null) {
            model.addAttribute("pet", existingProduct);
        } else {
            return "redirect:/admin/add-product";
        }
        model.addAttribute("pets", petService.findAllPet());
        model.addAttribute("page", "admin-add-products.jsp");
        return "admin-page";
    }

    @GetMapping("/add-product/search")
    public String searchProducts(@RequestParam("name-product") String name, Model model, @ModelAttribute("pet") Pet pet) {
        Pet searchResults = petService.findByNameContainingIgnoreCase(name);
        model.addAttribute("pet", searchResults);
        model.addAttribute("pets", petService.findAllPet());
        model.addAttribute("page", "admin-add-products.jsp");
        return "admin-page";
    }

}
