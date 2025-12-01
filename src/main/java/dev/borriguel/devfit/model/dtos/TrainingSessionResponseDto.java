package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TrainingSessionResponseDto(Long id, Long trainingPlanId, TrainingPlanResponseDto trainingPlan, int totalExercises, List<ExerciseSetResponseDto> exercises) {
}
