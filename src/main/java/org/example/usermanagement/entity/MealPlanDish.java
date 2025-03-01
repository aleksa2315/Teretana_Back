package org.example.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "meal_plan_dishes")
@Getter
@Setter
public class MealPlanDish {

    @EmbeddedId
    private MealPlanDishId id;

    @ManyToOne
    @MapsId("mealPlanId")
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id")
    private Dish dish;
}

@Embeddable
@Getter
@Setter
class MealPlanDishId implements Serializable {

    @Column(name = "meal_plan_id")
    private Long mealPlanId;

    @Column(name = "dish_id")
    private Long dishId;
}
