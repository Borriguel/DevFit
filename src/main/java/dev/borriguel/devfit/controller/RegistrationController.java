package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.UserMapper;
import dev.borriguel.devfit.model.dtos.ManagerRegistrationDto;
import dev.borriguel.devfit.model.dtos.MemberRegistrationDto;
import dev.borriguel.devfit.model.dtos.PersonalTrainerRegistrationDto;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import dev.borriguel.devfit.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService service;
    private final UserMapper mapper;

    @PostMapping("/manager")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerManager(@RequestBody ManagerRegistrationDto dto) {
        return mapper.toUserResponseDto(service.registerManager(dto));
    }

    @PostMapping("/personal")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerPersonalTrainer(@RequestBody PersonalTrainerRegistrationDto dto) {
        return mapper.toUserResponseDto(service.registerPersonalTrainer(dto));
    }

    @PostMapping("/member")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerMember(@RequestBody MemberRegistrationDto dto) {
        return mapper.toUserResponseDto(service.registerMember(dto));
    }
}
