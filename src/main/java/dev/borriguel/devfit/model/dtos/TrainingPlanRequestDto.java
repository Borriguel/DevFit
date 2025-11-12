package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

public record TrainingPlanRequestDto(String title, Goal goal, Long personalTrainerId, Long memberId) {
}
