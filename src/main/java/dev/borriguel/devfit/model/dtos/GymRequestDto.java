package dev.borriguel.devfit.model.dtos;

import java.math.BigDecimal;

public record GymRequestDto(String name, String address, BigDecimal monthlyFee) {
}
