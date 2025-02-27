package org.example.usermanagement.service;

import org.example.usermanagement.dtos.TrainingDTO;
import org.example.usermanagement.dtos.TrainingExerciseDTO;
import org.example.usermanagement.entity.*;
import org.example.usermanagement.repository.ExerciseRepository;
import org.example.usermanagement.repository.TrainingExerciseRepository;
import org.example.usermanagement.repository.TrainingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TrainingExerciseRepository trainingExerciseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TrainingDTO createOrUpdateTraining(TrainingDTO trainingDTO) {
        Training training = null;

        if (trainingDTO.getId() != null) {
            training = trainingRepository.findById(trainingDTO.getId()).orElse(new Training());
        } else {
            training = new Training();
        }

        training.setName(trainingDTO.getName());
        training.setDescription(trainingDTO.getDescription());

        // Očistimo stare vezbe
        training.getTrainingExercises().clear();

        // Dodajemo nove vezbe iz DTO-a
        Set<TrainingExercise> newExercises = new HashSet<>();
        if (trainingDTO.getTrainingExercises() != null) {
            for (TrainingExerciseDTO teDTO : trainingDTO.getTrainingExercises()) {
                Exercise exercise = exerciseRepository.findById(teDTO.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Exercise not found: " + teDTO.getExerciseId()));

                TrainingExercise te = new TrainingExercise();
                te.setId(new TrainingExerciseKey());
                te.setTraining(training);
                te.setExercise(exercise);
                te.setSets(teDTO.getSets());
                te.setReps(teDTO.getReps());

                newExercises.add(te);
            }
        }

        training.getTrainingExercises().addAll(newExercises);
        Training saved = trainingRepository.save(training);

        // Mapiramo nazad u DTO
        return convertToDTO(saved);
    }

    public TrainingDTO getTrainingById(Long id) {
        return trainingRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public Set<TrainingDTO> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet());
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    private TrainingDTO convertToDTO(Training training) {
        TrainingDTO dto = new TrainingDTO();
        dto.setId(training.getId());
        dto.setName(training.getName());
        dto.setDescription(training.getDescription());

        // Konverzija bridging entiteta u DTO
        dto.setTrainingExercises(training.getTrainingExercises().stream().map(te -> {
            TrainingExerciseDTO teDTO = new TrainingExerciseDTO();
            teDTO.setExerciseId(te.getExercise().getId());
            teDTO.setExerciseName(te.getExercise().getName());
            teDTO.setSets(te.getSets());
            teDTO.setReps(te.getReps());
            return teDTO;
        }).collect(Collectors.toList()));

        return dto;
    }
}
