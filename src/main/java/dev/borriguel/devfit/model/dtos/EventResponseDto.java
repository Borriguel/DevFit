package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventResponseDto(Long id, String title, String description, String location, LocalDateTime date, String gymUnit, int totalAttendees, List<ProfileResponseDto> attendees) {
}
