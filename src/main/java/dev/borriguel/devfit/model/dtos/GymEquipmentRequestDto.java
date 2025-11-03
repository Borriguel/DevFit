package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Category;

public record GymEquipmentRequestDto(String name, String description, String imageUrl, Category category) {
}
