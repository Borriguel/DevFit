package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PersonalTrainerResponseDto(Long id, String name, Long unitId, GymUnitResponseDto unit) {
}
