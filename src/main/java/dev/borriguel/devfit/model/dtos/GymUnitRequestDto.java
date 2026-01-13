package dev.borriguel.devfit.model.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record GymUnitRequestDto(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @NotBlank(message = "Address cannot be blank")
        @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
        String address,

        @NotNull(message = "Monthly fee cannot be null")
        @DecimalMin(value = "0.01", message = "Monthly fee must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Monthly fee cannot have more than 2 decimal places")
        BigDecimal monthlyFee
) {
}
