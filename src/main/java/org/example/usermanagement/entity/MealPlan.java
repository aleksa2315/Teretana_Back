package org.example.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "meal_plans")
@Getter
@Setter
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
    name = "meal_plan_dishes",
    joinColumns = @JoinColumn(name = "meal_plan_id"),
    inverseJoinColumns = @JoinColumn(name = "dish_id")
)
private List<Dish> dishes;

}
