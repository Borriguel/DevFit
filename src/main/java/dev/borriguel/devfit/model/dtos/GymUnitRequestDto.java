package dev.borriguel.devfit.model.dtos;

import java.math.BigDecimal;

public record GymUnitRequestDto(String name, String address, BigDecimal monthlyFee) {
}
