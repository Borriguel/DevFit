package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.UserMapper;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import dev.borriguel.devfit.model.dtos.UserUpdateRequestDto;
import dev.borriguel.devfit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public Page<UserResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @PreAuthorize("#id.equals(principal.id) or hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return mapper.toUserResponseDto(service.getById(id));
    }

    @PreAuthorize("#id.equals(principal.id) or hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PreAuthorize("#id.equals(principal.id) or hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public UserResponseDto updateById(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto dto) {
        var updatedUser = service.updateById(id, dto);
        return mapper.toUserResponseDto(updatedUser);
    }
}
