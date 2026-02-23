package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.ManagerMapper;
import dev.borriguel.devfit.model.dtos.ManagerResponseDto;
import dev.borriguel.devfit.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
@RequiredArgsConstructor
@Tag(name = "Gerentes", description = "Operações de gerenciamento de gerentes da academia")
public class ManagerController {
    private final ManagerService service;
    private final ManagerMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("#id.equals(principal.profileId) and hasRole('MANAGER') or hasRole('ADMIN')")
    @Operation(summary = "Buscar gerente por ID", description = "Retorna um gerente específico pelo ID. Pode expandir para incluir a unidade usando o parâmetro 'expand'")
    public ManagerResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandedDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os gerentes", description = "Retorna uma paginação de todos os gerentes da academia")
    public Page<ManagerResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#id.equals(principal.profileId) and hasRole('MANAGER') or hasRole('ADMIN')")
    @Operation(summary = "Excluir gerente", description = "Exclui um gerente pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
