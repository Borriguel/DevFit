package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.borriguel.devfit.model.Goal;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberResponseDto(Long id, String name, Goal goal, BigDecimal weight, BigDecimal height, Long unitId, GymUnitResponseDto unit) {
}
