package org.example.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "meal_plan_dishes")
@Getter
@Setter
public class MealPlanDish {

    @EmbeddedId
    private MealPlanDishId id = new MealPlanDishId(); // Ensure ID is never null

    @ManyToOne
    @MapsId("mealPlanId")
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
        this.id.setMealPlanId(mealPlan.getId()); // Ensure mealPlanId is set
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        this.id.setDishId(dish.getId()); // Ensure dishId is set
    }
}
