package dev.borriguel.devfit.model.dtos;

public record MemberRegistrationDto(String email, String password, String name, String document, Long gymId) {
}
