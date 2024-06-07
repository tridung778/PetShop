package com.example.ASM.database;

import com.example.ASM.configs.SecurityConfig;
import com.example.ASM.enums.Gender;
import com.example.ASM.enums.PetStatus;
import com.example.ASM.enums.Role;
import com.example.ASM.models.*;
import com.example.ASM.services.PetCategoryService;
import com.example.ASM.services.PetService;
import com.example.ASM.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.*;


@Configuration
public class DB {

    private static final Logger logger = LoggerFactory.getLogger(DB.class);

    @Bean
    CommandLineRunner initDatabase(PetCategoryService petCategoryService, PetService petService, UserService userService) {
        return args -> {
            String[] petCategories = {"Dog", "Cat"};
            String[] dogNames = {"Lucky", "Max", "Charlie", "Buddy", "Cooper", "Rocky", "Duke", "Bear", "Teddy", "Leo"};
            String[] catNames = {"Bella", "Lucy", "Lily", "Chloe", "Sophie", "Lola", "Daisy", "Molly", "Luna", "Penny"};
            String[] dogBreeds = {"Labrador Retriever", "German Shepherd", "Golden Retriever", "Bulldog", "Beagle", "Poodle", "Rottweiler", "Yorkshire Terrier", "Boxer", "Siberian Husky"};
            String[] catBreeds = {"Maine Coon", "Siamese", "Persian", "Ragdoll", "British Shorthair", "Bengal", "Scottish Fold", "Sphynx", "Abyssinian", "Russian Blue"};

            String[] birdNames = {"Rio", "Coco", "Mango", "Kiwi", "Sunny", "Sky", "Blue", "Joey", "Charlie", "Ollie"};
            String[] fishNames = {"Nemo", "Dory", "Bubbles", "Goldie", "Finley", "Marlin", "Coral", "Gill", "Pearl", "Splash"};

            String[] foodCategories = {"Thức ăn khô", "Thức ăn ướt", "Bánh thưởng", "Sữa", "Vitamin", "Thực phẩm chức năng", "Pate", "Xúc xích", "Snack", "Cỏ khô"};
            String[] foodBrands = {"Royal Canin", "Pedigree", "Whiskas", "Me-O", "Purina", "Hill's", "Eukanuba", "Iams", "Science Diet", "Taste of the Wild"};

            String[] toyCategories = {"Bóng", "Xương gặm", "Chuột nhồi bông", "Cần câu mèo", "Thú nhồi bông", "Gối nhai", "Đĩa bay", "Đồ chơi thông minh", "Bàn cào móng", "Lồng & Chuồng"};

            // Pet Categories
            for (String category : petCategories) {
                // Kiểm tra xem PetCategory đã tồn tại chưa
                Optional<PetCategory> existingCategory = petCategoryService.getPetCategoryByName(category);

                if (existingCategory.isPresent()) {
                    // Nếu đã tồn tại, cập nhật bản ghi hiện có
                    logger.info("PetCategory with name '{}' already exists. Updating existing record.", category);
                    PetCategory categoryToUpdate = existingCategory.get();
                    // Cập nhật thuộc tính của PetCategory nếu cần
                    petCategoryService.savePetCategory(categoryToUpdate);
                } else {
                    // Nếu chưa tồn tại, chèn bản ghi mới
                    PetCategory petCategory = new PetCategory();
                    petCategory.setId(UUID.randomUUID());
                    petCategory.setName(category);
                    petCategoryService.savePetCategory(petCategory);
                }
            }

            List<String> dogAndCatNames = new ArrayList<>();
            dogAndCatNames.addAll(Arrays.asList(dogNames));
            dogAndCatNames.addAll(Arrays.asList(catNames));

            List<String> dogAndCatBreeds = new ArrayList<>();
            dogAndCatBreeds.addAll(Arrays.asList(dogBreeds));
            dogAndCatBreeds.addAll(Arrays.asList(catBreeds));


            // Pets
            for (int i = 0; i < 100; i++) {
                Pet pet = new Pet();
                String petName = dogAndCatNames.get(i % dogAndCatBreeds.size()); // Sử dụng toán tử % để lặp lại tên trong mảng
                pet.setName(petName + " " + i); // Thêm chỉ số i vào tên thú cưng
                if (i < 50) {
                    pet.setBreed(dogBreeds[i % dogBreeds.length]);
                    pet.setImage("dog.jpg");
                    Optional<PetCategory> dogCategory = petCategoryService.getPetCategoryByName("Dog");
                    dogCategory.ifPresent(pet::setCategory);
                } else {
                    pet.setBreed(catBreeds[i % catBreeds.length]);
                    pet.setImage("cat.jpg");
                    Optional<PetCategory> catCategory = petCategoryService.getPetCategoryByName("Cat");
                    catCategory.ifPresent(pet::setCategory);
                }
                pet.setId(UUID.randomUUID());
                pet.setAge(i % 15 + 1); // Tuổi từ 1 đến 15
                pet.setGender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE);
                pet.setDescription("Mô tả thú cưng " + (i + 1));
                pet.setPrice(1000000.0 * (i % 10) + 1000000); // Giá từ 0 đến 9 triệu
                pet.setStatus(PetStatus.AVAILABLE);
                petService.savePet(pet);
            }

            // Users
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setName("Tri ngu");
            user.setUsername("user");
            user.setEmail("user@user");
            user.setPassword(SecurityConfig.encode("123"));
            user.setRole(Role.USER);
            user.setPhoto("/images/"+"cat.jpg");
            user.setPhone("0343285405");
            userService.register(user);

            User admin = new User();
            admin.setId(UUID.randomUUID());
            admin.setName("Dung dep rai");
            admin.setUsername("admin");
            admin.setEmail("duongtridung07@gmail.com");
            admin.setPassword(SecurityConfig.encode("123"));
            admin.setRole(Role.ADMIN);
            admin.setPhone("0556285405");
            userService.register(admin);
        };
    }
}
