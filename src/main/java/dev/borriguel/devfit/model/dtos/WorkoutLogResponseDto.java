package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WorkoutLogResponseDto(Long id, Long sessionId, TrainingSessionResponseDto session, LocalDate performedOn) {
}
