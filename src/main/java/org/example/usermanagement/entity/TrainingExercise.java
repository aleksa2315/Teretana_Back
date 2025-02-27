package org.example.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "training_exercises")
@Getter
@Setter
public class TrainingExercise {

    @EmbeddedId
    private TrainingExerciseKey id = new TrainingExerciseKey();

    @ManyToOne
    @MapsId("trainingId")
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @MapsId("exerciseId")
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int sets;
    private int reps;
}
