package org.example.usermanagement.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealPlanDTO {
    private Long id;
    private String name;
    private List<Long> dishes;
}
