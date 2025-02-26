package org.example.usermanagement.dtos;

import lombok.Data;

@Data
public class ExerciseDTO {

    private Long id;
    private String name;
    private int sets;
    private int repetitions;
}
