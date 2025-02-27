package org.example.usermanagement.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class TrainingExerciseKey implements Serializable {

    private Long trainingId;
    private Long exerciseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingExerciseKey)) return false;
        TrainingExerciseKey that = (TrainingExerciseKey) o;
        return Objects.equals(trainingId, that.trainingId) &&
                Objects.equals(exerciseId, that.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, exerciseId);
    }
}
