package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record MemberRegistrationDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @NotBlank(message = "Document cannot be blank")
        @Pattern(regexp = "^[0-9]{11}$", message = "Invalid document format")
        @Size(min = 11, max = 11, message = "Document must be exactly 11 characters long")
        String document,

        @NotNull(message = "Gym ID cannot be null")
        @Positive(message = "Gym ID must be a positive number")
        Long gymId,

        @NotNull(message = "Goal cannot be null")
        Goal goal,

        @DecimalMin(value = "0.0", inclusive = true, message = "Weight cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Weight cannot have more than 2 decimal places")
        BigDecimal weight,

        @DecimalMin(value = "0.0", inclusive = true, message = "Height cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Height cannot have more than 2 decimal places")
        BigDecimal height
) {
}
