package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Goal;

import java.math.BigDecimal;

public record MemberRegistrationDto(String email, String password, String name, String document, Long gymId, Goal goal, BigDecimal weight, BigDecimal height) {
}
