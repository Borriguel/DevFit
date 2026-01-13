package dev.borriguel.devfit.model.dtos;

import dev.borriguel.devfit.model.Category;

import jakarta.validation.constraints.*;

public record GymEquipmentRequestDto(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
        String name,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String description,

        @NotBlank(message = "Image URL cannot be blank")
        @Size(max = 255, message = "Image URL cannot be longer than 255 characters")
        String imageUrl,

        @NotNull(message = "Category cannot be null")
        Category category
) {
}
