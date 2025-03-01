package org.example.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
class MealPlanDishId implements Serializable {

    @Column(name = "meal_plan_id")
    private Long mealPlanId;

    @Column(name = "dish_id")
    private Long dishId;

    // Explicit setters to ensure modification is possible
    public void setMealPlanId(Long mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }
}
