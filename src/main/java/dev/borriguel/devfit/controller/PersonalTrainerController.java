package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.PersonalTrainerMapper;
import dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto;
import dev.borriguel.devfit.service.PersonalTrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal-trainer")
@RequiredArgsConstructor
@Tag(name = "Personal Trainers", description = "Operações de gerenciamento de personal trainers")
public class PersonalTrainerController {
    private final PersonalTrainerService service;
    private final PersonalTrainerMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasRole('PERSONAL_TRAINER') and #id == principal.profileId")
    @Operation(summary = "Buscar personal trainer por ID", description = "Retorna um personal trainer específico pelo ID. Pode expandir para incluir a unidade usando o parâmetro 'expand'")
    public PersonalTrainerResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/unit/{unitId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Listar personal trainers por unidade", description = "Retorna uma paginação de personal trainers de uma unidade específica")
    public Page<PersonalTrainerResponseDto> getAllByUnitId(@PathVariable Long unitId, @ParameterObject Pageable page) {
        return service.getAllByUnitIdAsDto(unitId, page);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os personal trainers", description = "Retorna uma paginação de todos os personal trainers da academia")
    public Page<PersonalTrainerResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }
}
