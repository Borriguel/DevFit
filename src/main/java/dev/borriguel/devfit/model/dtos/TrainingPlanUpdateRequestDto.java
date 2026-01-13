package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TrainingPlanUpdateRequestDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 20, message = "Title must be between 3 and 20 characters")
        String title,

        @NotNull(message = "Goal cannot be null")
        Goal goal,

        @NotNull(message = "End date cannot be null")
        @FutureOrPresent(message = "End date must be today or in the future")
        LocalDate endDate
) {
}
