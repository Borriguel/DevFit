package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Category;

public record GymEquipmentResponseDto(Long id, String name, String description, String imageUrl, Category category) {
}
