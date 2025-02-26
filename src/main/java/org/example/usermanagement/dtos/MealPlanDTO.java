package org.example.usermanagement.dtos;

import lombok.Data;
import java.util.List;

@Data
public class MealPlanDTO {

    private Long id;
    private String name;
    private List<DishDTO> dishes;
}
