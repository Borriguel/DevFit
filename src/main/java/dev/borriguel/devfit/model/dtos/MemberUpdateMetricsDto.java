package dev.borriguel.devfit.model.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record MemberUpdateMetricsDto(
        @DecimalMin(value = "0.0", inclusive = true, message = "Weight cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Weight cannot have more than 2 decimal places")
        BigDecimal weight,

        @DecimalMin(value = "0.0", inclusive = true, message = "Height cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Height cannot have more than 2 decimal places")
        BigDecimal height) {
}
