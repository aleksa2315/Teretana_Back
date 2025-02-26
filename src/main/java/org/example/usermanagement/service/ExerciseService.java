package org.example.usermanagement.service;

import org.example.usermanagement.dtos.ExerciseDTO;
import org.example.usermanagement.entity.Exercise;
import org.example.usermanagement.repository.ExerciseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(exercise -> modelMapper.map(exercise, ExerciseDTO.class))
                .collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .map(exercise -> modelMapper.map(exercise, ExerciseDTO.class))
                .orElse(null);
    }

    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return modelMapper.map(savedExercise, ExerciseDTO.class);
    }

    public ExerciseDTO updateExercise(Long id, ExerciseDTO exerciseDTO) {
        if (exerciseRepository.existsById(id)) {
            Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
            exercise.setId(id);
            Exercise updatedExercise = exerciseRepository.save(exercise);
            return modelMapper.map(updatedExercise, ExerciseDTO.class);
        }
        return null;
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
