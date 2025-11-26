package dev.borriguel.devfit.service;

import dev.borriguel.devfit.model.ExerciseSet;
import dev.borriguel.devfit.model.dtos.ExerciseSetRequestDto;
import dev.borriguel.devfit.repository.ExerciseSetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseSetService {
    private final ExerciseSetRepository repository;
    private final TrainingSessionService trainingSessionService;
    private final GymEquipmentService gymEquipmentService;

    @Transactional
    public ExerciseSet createExerciseSet(ExerciseSetRequestDto dto) {
        var trainingSession = trainingSessionService.getById(dto.trainingSessionId());
        var equipment = gymEquipmentService.getById(dto.GymEquipmentId());
        var exerciseSet = new ExerciseSet(equipment, dto.reps(), dto.sets());
        exerciseSet.assignSession(trainingSession);
        return repository.save(exerciseSet);
    }

    public ExerciseSet getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise set not found"));
    }

    public ExerciseSet getByIdWithEquipment(Long id) {
        return repository.findByIdWithEquipment(id).orElseThrow(() -> new IllegalArgumentException("Exercise set not found"));
    }

    public List<ExerciseSet> getAllByTrainingSession(Long trainingSessionId) {
        return repository.findBySession_Id(trainingSessionId);
    }

    @Transactional
    public void deleteById(Long id) {
        var exerciseSet = getById(id);
        repository.delete(exerciseSet);
    }
}
