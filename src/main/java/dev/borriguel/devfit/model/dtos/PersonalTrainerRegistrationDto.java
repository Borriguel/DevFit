package dev.borriguel.devfit.model.dtos;

public record PersonalTrainerRegistrationDto(String email, String password, String name, String document, Long gymId) {
}
