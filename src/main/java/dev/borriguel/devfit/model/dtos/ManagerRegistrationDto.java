package dev.borriguel.devfit.model.dtos;

public record ManagerRegistrationDto(String email, String password, String name, String document, Long gymId) {
}
