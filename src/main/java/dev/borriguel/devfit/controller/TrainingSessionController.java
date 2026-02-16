package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.TrainingSessionMapper;
import dev.borriguel.devfit.model.dtos.TrainingSessionResponseDto;
import dev.borriguel.devfit.service.TrainingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-session")
@RequiredArgsConstructor
public class TrainingSessionController {
    private final TrainingSessionService service;
    private final TrainingSessionMapper mapper;

    @PostMapping("/{trainingPlanId}/{personalId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    public TrainingSessionResponseDto createTrainingSession(@PathVariable Long trainingPlanId, @PathVariable Long personalId) {
        return mapper.toSimpleDto(service.createTrainingSession(trainingPlanId, personalId));
    }

    @GetMapping("/{id}")
    public TrainingSessionResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandTrainingPlan = expand != null && expand.contains("training-plan");
        boolean expandExercises = expand != null && expand.contains("exercises");
        if (expandTrainingPlan && expandExercises) return mapper.toExpandDto(service.getByIdWithTrainingPlanAndExercises(id));
        if (expandTrainingPlan) return mapper.toExpandWithTrainingPlanOnly(service.getByIdWithTrainingPlan(id));
        if (expandExercises) return mapper.toExpandWithExercisesOnly(service.getByIdWithExercises(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/training-session/{trainingSessionId}")
    public List<TrainingSessionResponseDto> getAllByTrainingSessionId(@PathVariable Long trainingSessionId) {
        return service.getAllByTrainingPlanAsDto(trainingSessionId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
