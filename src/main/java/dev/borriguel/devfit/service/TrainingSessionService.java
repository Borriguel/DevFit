package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.TrainingSession;
import dev.borriguel.devfit.repository.TrainingSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSessionService {
    private final TrainingSessionRepository repository;
    private final TrainingPlanService trainingPlanService;
    private final PersonalTrainerService personalService;

    @Transactional
    public TrainingSession createTrainingSession(Long trainingPlanId, Long personalId) {
        var trainingPlan = trainingPlanService.getById(trainingPlanId);
        var personal = personalService.getById(personalId);
        var trainingSession = new TrainingSession();
        trainingSession.assignPlan(trainingPlan, personal);
        return repository.save(trainingSession);
    }

    public TrainingSession getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Training session not found with id: " + id));
    }

    public TrainingSession getByIdWithTrainingPlanAndExercises(Long id) {
        return repository.findByIdWithTrainingPlanAndExercises(id).orElseThrow(() -> new ResourceNotFound("Training session not found with id: " + id));
    }

    public TrainingSession getByIdWithExercises(Long id) {
        return repository.findByIdWithExercises(id).orElseThrow(() -> new ResourceNotFound("Training session not found with id: " + id));
    }

    public TrainingSession getByIdWithTrainingPlan(Long id) {
        return repository.findByIdWithTrainingPlan(id).orElseThrow(() -> new ResourceNotFound("Training session not found with id: " + id));
    }

    public List<TrainingSession> getAllByTrainingPlan(Long trainingPlanId) {
        return repository.findByTrainingPlan_Id(trainingPlanId);
    }

    @Transactional
    public void deleteById(Long id) {
        var trainingSession = getById(id);
        repository.delete(trainingSession);
    }
}
