package dev.borriguel.devfit.model.dtos;

public record ExerciseSetRequestDto(Long GymEquipmentId, Long trainingSessionId, int reps, int sets) {
}
