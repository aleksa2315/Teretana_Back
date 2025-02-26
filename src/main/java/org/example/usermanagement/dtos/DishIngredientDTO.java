package org.example.usermanagement.dtos;

import lombok.Data;

@Data
public class DishIngredientDTO {
    private Long ingredientId;
    private String ingredientName;
    private double weight;

    // Getteri i setteri
}
