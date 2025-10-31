package dev.borriguel.devfit.model.dtos;

import java.time.LocalDateTime;

public record EventRequestDto(String title, String description, String location, LocalDateTime date, Long gymUnitId) {
}
