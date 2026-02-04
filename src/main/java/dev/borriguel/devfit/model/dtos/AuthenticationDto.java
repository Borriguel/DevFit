package dev.borriguel.devfit.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password) {
}
