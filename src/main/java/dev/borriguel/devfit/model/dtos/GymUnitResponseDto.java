package dev.borriguel.devfit.model.dtos;

import java.math.BigDecimal;

public record GymUnitResponseDto(Long id, String name, String address, BigDecimal monthlyFee) {
}
