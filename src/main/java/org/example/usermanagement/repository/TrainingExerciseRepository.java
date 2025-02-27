package org.example.usermanagement.repository;

import org.example.usermanagement.entity.TrainingExercise;
import org.example.usermanagement.entity.TrainingExerciseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, TrainingExerciseKey> {
}
