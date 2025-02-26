package org.example.usermanagement.service;

import org.example.usermanagement.dtos.TrainingDTO;
import org.example.usermanagement.entity.Training;
import org.example.usermanagement.repository.TrainingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TrainingDTO> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(training -> modelMapper.map(training, TrainingDTO.class))
                .collect(Collectors.toList());
    }

    public TrainingDTO getTrainingById(Long id) {
        return trainingRepository.findById(id)
                .map(training -> modelMapper.map(training, TrainingDTO.class))
                .orElse(null);
    }

    public TrainingDTO createTraining(TrainingDTO trainingDTO) {
        Training training = modelMapper.map(trainingDTO, Training.class);
        Training savedTraining = trainingRepository.save(training);
        return modelMapper.map(savedTraining, TrainingDTO.class);
    }

    public TrainingDTO updateTraining(Long id, TrainingDTO trainingDTO) {
        if (trainingRepository.existsById(id)) {
            Training training = modelMapper.map(trainingDTO, Training.class);
            training.setId(id);
            Training updatedTraining = trainingRepository.save(training);
            return modelMapper.map(updatedTraining, TrainingDTO.class);
        }
        return null;
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
