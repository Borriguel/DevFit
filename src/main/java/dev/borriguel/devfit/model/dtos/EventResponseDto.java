package dev.borriguel.devfit.model.dtos;

import java.time.LocalDateTime;

public record EventResponseDto(Long id, String title, String description, String location, LocalDateTime date, String unitName, int totalAttendees) {
}
