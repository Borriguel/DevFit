package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

import jakarta.validation.constraints.*;

public record TrainingPlanRequestDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 20, message = "Title must be between 3 and 20 characters")
        String title,

        @NotNull(message = "Goal cannot be null")
        Goal goal,

        @NotNull(message = "Personal trainer ID cannot be null")
        @Positive(message = "Personal trainer ID must be a positive number")
        Long personalTrainerId,

        @NotNull(message = "Member ID cannot be null")
        @Positive(message = "Member ID must be a positive number")
        Long memberId
) {
}
