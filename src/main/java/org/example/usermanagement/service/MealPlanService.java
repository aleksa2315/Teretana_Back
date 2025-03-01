package org.example.usermanagement.service;

import org.example.usermanagement.dtos.MealPlanDTO;
import org.example.usermanagement.entity.MealPlan;
import org.example.usermanagement.repository.MealPlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    public MealPlan createMealPlan(MealPlan mealPlan) {
        // Save MealPlan first so it gets an ID
        MealPlan savedMealPlan = mealPlanRepository.save(mealPlan);

        // Ensure mealPlanDishes are properly linked
        if (savedMealPlan.getMealPlanDishes() != null) {
            for (MealPlanDish dish : savedMealPlan.getMealPlanDishes()) {
                dish.setMealPlan(savedMealPlan); // Assign the saved MealPlan
                dish.getId().setMealPlanId(savedMealPlan.getId()); // Assign ID explicitly
            }
        }

        return mealPlanRepository.save(savedMealPlan);
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
