package org.example.usermanagement.dtos;

import lombok.Data;
import java.util.List;

@Data
public class TrainingDTO {

    private Long id;
    private String name;
    private String estimatedDuration;
    private List<ExerciseDTO> exercises;
}
