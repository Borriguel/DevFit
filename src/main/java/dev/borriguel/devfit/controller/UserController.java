package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.UserMapper;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import dev.borriguel.devfit.model.dtos.UserUpdateRequestDto;
import dev.borriguel.devfit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários do sistema")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma paginação de todos os usuários do sistema")
    public Page<UserResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @PreAuthorize("#id.equals(principal.id) or hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID")
    public UserResponseDto getById(@PathVariable Long id) {
        return mapper.toUserResponseDto(service.getById(id));
    }

    @PreAuthorize("#id.equals(principal.id) or hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PreAuthorize("#id.equals(principal.id) or hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente pelo ID")
    public UserResponseDto updateById(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto dto) {
        var updatedUser = service.updateById(id, dto);
        return mapper.toUserResponseDto(updatedUser);
    }
}
