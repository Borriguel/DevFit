package dev.borriguel.devfit.model.dtos;

import jakarta.validation.constraints.*;

public record ExerciseSetRequestDto(
        @NotNull(message = "Gym equipment ID cannot be null")
        @Positive(message = "Gym equipment ID must be a positive number")
        Long GymEquipmentId,

        @NotNull(message = "Training session ID cannot be null")
        @Positive(message = "Training session ID must be a positive number")
        Long trainingSessionId,

        @Min(value = 0, message = "Reps cannot be negative")
        int reps,

        @Min(value = 0, message = "Sets cannot be negative")
        int sets
) {
}
