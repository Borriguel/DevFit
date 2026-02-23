package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.UserMapper;
import dev.borriguel.devfit.model.dtos.ManagerRegistrationDto;
import dev.borriguel.devfit.model.dtos.MemberRegistrationDto;
import dev.borriguel.devfit.model.dtos.PersonalTrainerRegistrationDto;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import dev.borriguel.devfit.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Tag(name = "Registros", description = "Operações de registro de novos usuários no sistema")
public class RegistrationController {
    private final RegistrationService service;
    private final UserMapper mapper;

    @PostMapping("/manager")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar gerente", description = "Registra um novo gerente no sistema")
    public UserResponseDto registerManager(@RequestBody @Valid ManagerRegistrationDto dto) {
        return mapper.toUserResponseDto(service.registerManager(dto));
    }

    @PostMapping("/personal")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Registrar personal trainer", description = "Registra um novo personal trainer no sistema")
    public UserResponseDto registerPersonalTrainer(@RequestBody @Valid PersonalTrainerRegistrationDto dto) {
        return mapper.toUserResponseDto(service.registerPersonalTrainer(dto));
    }

    @PostMapping("/member")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar membro", description = "Registra um novo membro no sistema")
    public UserResponseDto registerMember(@RequestBody @Valid MemberRegistrationDto dto) {
        return mapper.toUserResponseDto(service.registerMember(dto));
    }
}
