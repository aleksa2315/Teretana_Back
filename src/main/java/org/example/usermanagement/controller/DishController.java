package org.example.usermanagement.controller;

import org.example.usermanagement.dtos.DishDTO;
import org.example.usermanagement.dtos.DishIngredientDTO;
import org.example.usermanagement.entity.Dish;
import org.example.usermanagement.entity.DishIngredient;
import org.example.usermanagement.entity.Ingredient;
import org.example.usermanagement.service.DishService;
import org.example.usermanagement.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        List<DishDTO> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Long id) {
        DishDTO dish = dishService.getDishById(id);
        if (dish != null) {
            return ResponseEntity.ok(dish);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody DishDTO dishDTO) {
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());

        Set<DishIngredient> dishIngredients = mapDishIngredients(dishDTO, dish);
        dish = dishService.createDishWithIngredients(dish, dishIngredients);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(dish, DishDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @RequestBody DishDTO dishDTO) {
        Dish existingDish = dishService.getDishEntityById(id);
        if (existingDish == null) {
            return ResponseEntity.notFound().build();
        }

        existingDish.setName(dishDTO.getName());
        Set<DishIngredient> dishIngredients = mapDishIngredients(dishDTO, existingDish);
        existingDish = dishService.createDishWithIngredients(existingDish, dishIngredients);

        return ResponseEntity.ok(modelMapper.map(existingDish, DishDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    private Set<DishIngredient> mapDishIngredients(DishDTO dishDTO, Dish dish) {
        Set<DishIngredient> dishIngredients = new HashSet<>();

        for (DishIngredientDTO dto : dishDTO.getIngredients()) {
            Ingredient ingredient = ingredientService.getIngredientById(dto.getIngredientId());
            if (ingredient != null) {
                DishIngredient dishIngredient = new DishIngredient();
                dishIngredient.setDish(dish);
                dishIngredient.setIngredient(ingredient);
                dishIngredient.setWeight(dto.getWeight());
                dishIngredients.add(dishIngredient);
            }
        }

        return dishIngredients;
    }
}
