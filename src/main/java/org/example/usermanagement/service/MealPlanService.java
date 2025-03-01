package org.example.usermanagement.service;

import org.example.usermanagement.dtos.MealPlanDTO;
import org.example.usermanagement.entity.Dish;
import org.example.usermanagement.entity.MealPlan;
import org.example.usermanagement.entity.MealPlanDish;
import org.example.usermanagement.entity.MealPlanDishId;
import org.example.usermanagement.repository.DishRepository;
import org.example.usermanagement.repository.MealPlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private final DishRepository dishRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MealPlanService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<MealPlanDTO> getAllMealPlans() {
        return mealPlanRepository.findAll().stream()
                .map(mealPlan -> modelMapper.map(mealPlan, MealPlanDTO.class))
                .collect(Collectors.toList());
    }

    public MealPlanDTO getMealPlanById(Long id) {
        return mealPlanRepository.findById(id)
                .map(mealPlan -> modelMapper.map(mealPlan, MealPlanDTO.class))
                .orElse(null);
    }


    public MealPlanDTO createMealPlan(MealPlanDTO mealPlanDTO) {
        MealPlan mealPlan = new MealPlan();
        mealPlan.setName(mealPlanDTO.getName());

        // Save the MealPlan first so it gets an ID
        mealPlan = mealPlanRepository.save(mealPlan);

        List<MealPlanDish> mealPlanDishes = new ArrayList<>();
        if (mealPlanDTO.getDishes() != null) {
            for (Long dishId : mealPlanDTO.getDishes()) {
                Dish dish = dishRepository.findById(dishId)
                        .orElseThrow(() -> new RuntimeException("Dish not found: " + dishId));

                MealPlanDish mealPlanDish = new MealPlanDish();
                mealPlanDish.setMealPlan(mealPlan);
                mealPlanDish.setDish(dish);

                // Ensure ID is set
                MealPlanDishId mealPlanDishId = new MealPlanDishId();
                mealPlanDishId.setMealPlanId(mealPlan.getId());
                mealPlanDishId.setDishId(dish.getId());
                mealPlanDish.setId(mealPlanDishId);

                mealPlanDishes.add(mealPlanDish);
            }
        }

        mealPlan.setMealPlanDishes(mealPlanDishes);
        mealPlan = mealPlanRepository.save(mealPlan);

        // Convert back to DTO for response
        return new MealPlanDTO(mealPlan.getId(), mealPlan.getName(),
                mealPlan.getMealPlanDishes().stream()
                        .map(dish -> dish.getDish().getId())
                        .collect(Collectors.toList()));
    }


    public MealPlanDTO updateMealPlan(Long id, MealPlanDTO mealPlanDTO) {
        if (mealPlanRepository.existsById(id)) {
            MealPlan mealPlan = modelMapper.map(mealPlanDTO, MealPlan.class);
            mealPlan.setId(id);
            MealPlan updatedMealPlan = mealPlanRepository.save(mealPlan);
            return modelMapper.map(updatedMealPlan, MealPlanDTO.class);
        }
        return null;
    }

    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
}
