package org.example.usermanagement.service;

import org.example.usermanagement.dtos.DishDTO;
import org.example.usermanagement.entity.*;
import org.example.usermanagement.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DishDTO> getAllDishes() {
        return dishRepository.findAll().stream()
                .map(dish -> modelMapper.map(dish, DishDTO.class))
                .collect(Collectors.toList());
    }

    public DishDTO getDishById(Long id) {
        return dishRepository.findById(id)
                .map(dish -> modelMapper.map(dish, DishDTO.class))
                .orElse(null);
    }

    public Dish getDishEntityById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish createDishWithIngredients(Dish dish, Set<DishIngredient> ingredients) {
        dish.getIngredients().clear();
        dish.getIngredients().addAll(ingredients);
        return dishRepository.save(dish);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }
}
