package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExerciseSetResponseDto(Long id, int reps, int sets, Long equipmentId, GymEquipmentResponseDto equipment) {
}
