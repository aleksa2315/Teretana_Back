package org.example.usermanagement.controller;

import org.example.usermanagement.dtos.TrainingDTO;
import org.example.usermanagement.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @GetMapping
    public ResponseEntity<Set<TrainingDTO>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTrainings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) {
        TrainingDTO dto = trainingService.getTrainingById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody TrainingDTO trainingDTO) {
        TrainingDTO saved = trainingService.createOrUpdateTraining(trainingDTO);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id, @RequestBody TrainingDTO trainingDTO) {
        // Uveri se da postavimo ID
        trainingDTO.setId(id);
        TrainingDTO updated = trainingService.createOrUpdateTraining(trainingDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }
}
