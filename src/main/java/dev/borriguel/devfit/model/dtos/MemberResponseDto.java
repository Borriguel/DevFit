package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberResponseDto(Long id, String name, String goal, BigDecimal weight, BigDecimal height, Long unitId, GymUnitResponseDto unit) {
}
