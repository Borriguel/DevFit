package dev.borriguel.devfit.model.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record EventRequestDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
        String title,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String description,

        @NotBlank(message = "Location cannot be blank")
        @Size(max = 80, message = "Location cannot be longer than 80 characters")
        String location,

        @NotNull(message = "Date cannot be null")
        @FutureOrPresent(message = "Date must be today or in the future")
        LocalDateTime date,

        @NotNull(message = "Gym unit ID cannot be null")
        @Positive(message = "Gym unit ID must be a positive number")
        Long gymUnitId
) {
}
