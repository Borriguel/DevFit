package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

import java.time.LocalDate;

public record TrainingPlanResponseDto(Long id, String title, Goal goal, LocalDate startDate, LocalDate endDate, Long personalId, Long memberId) {
}
