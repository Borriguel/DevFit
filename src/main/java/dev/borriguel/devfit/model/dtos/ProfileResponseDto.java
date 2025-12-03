package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfileResponseDto(Long id, String name, String document) {
}