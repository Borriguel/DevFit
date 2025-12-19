package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.WorkoutLog;
import dev.borriguel.devfit.repository.WorkoutLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {
    private final WorkoutLogRepository repository;
    private final TrainingSessionService trainingSessionService;

    @Transactional
    public WorkoutLog createWorkoutLog(Long trainingSessionId) {
        var trainingSession = trainingSessionService.getById(trainingSessionId);
        var workoutLog = new WorkoutLog(trainingSession);
        workoutLog.markAsPerformed();
        return repository.save(workoutLog);
    }

    public WorkoutLog getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Workout log not found with id: " + id));
    }

    public WorkoutLog getByIdWithSession(Long id) {
        return repository.findByIdWithSession(id).orElseThrow(() -> new ResourceNotFound("Workout log not found with id: " + id));
    }

    @Transactional
    public void deleteById(Long id) {
        var workoutLog = getById(id);
        repository.delete(workoutLog);
    }
}
