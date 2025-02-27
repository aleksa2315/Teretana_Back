package org.example.usermanagement.dtos;

import lombok.Data;

@Data
public class TrainingExerciseDTO {
    private Long exerciseId;
    private String exerciseName;
    private int sets;
    private int reps;
}
