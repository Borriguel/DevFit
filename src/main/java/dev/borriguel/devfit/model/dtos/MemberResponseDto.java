package dev.borriguel.devfit.model.dtos;

import java.math.BigDecimal;

public record MemberResponseDto(Long id, String name, String goal, BigDecimal weight, BigDecimal height) {
}
