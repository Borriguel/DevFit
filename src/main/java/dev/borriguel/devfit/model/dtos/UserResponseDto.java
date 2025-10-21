package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Role;

public record UserResponseDto(Long id, String email, Role role, ProfileResponseDto profile) {
}

