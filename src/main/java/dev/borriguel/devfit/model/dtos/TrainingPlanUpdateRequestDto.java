package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

import java.time.LocalDate;

public record TrainingPlanUpdateRequestDto(String title, Goal goal, LocalDate endDate) {
}
