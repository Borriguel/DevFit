package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.GymEquipmentMapper;
import dev.borriguel.devfit.model.Category;
import dev.borriguel.devfit.model.dtos.GymEquipmentRequestDto;
import dev.borriguel.devfit.model.dtos.GymEquipmentResponseDto;
import dev.borriguel.devfit.service.GymEquipmentService;
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
@RequestMapping("/gym-equipment")
@RequiredArgsConstructor
@Tag(name = "Equipamentos de Academia", description = "Operações de gerenciamento de equipamentos da academia")
public class GymEquipmentController {
    private final GymEquipmentService service;
    private final GymEquipmentMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar equipamento", description = "Cria um novo equipamento na academia")
    public GymEquipmentResponseDto createGymEquipment(@RequestBody @Valid GymEquipmentRequestDto dto) {
        var equipment = service.createGymEquipment(mapper.toGymEquipment(dto));
        return mapper.toGymEquipmentResponseDto(equipment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipamento por ID", description = "Retorna um equipamento específico pelo ID")
    public GymEquipmentResponseDto getById(@PathVariable Long id) {
        return mapper.toGymEquipmentResponseDto(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER')")
    @Operation(summary = "Listar todos os equipamentos", description = "Retorna uma paginação de todos os equipamentos da academia")
    public Page<GymEquipmentResponseDto> getAll(@ParameterObject Pageable pageable) {
        return service.getAll(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir equipamento", description = "Exclui um equipamento pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar equipamento", description = "Atualiza um equipamento existente pelo ID")
    public GymEquipmentResponseDto updateById(@PathVariable Long id, @RequestBody @Valid GymEquipmentRequestDto dto) {
        var updatedEquipment = service.updateById(id, mapper.toGymEquipment(dto));
        return mapper.toGymEquipmentResponseDto(updatedEquipment);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER')")
    @Operation(summary = "Listar equipamentos por categoria", description = "Retorna uma paginação de equipamentos filtrados por categoria")
    public Page<GymEquipmentResponseDto> getAllByCategory(@PathVariable Category category, @ParameterObject Pageable pageable) {
        return service.getAllByCategory(category, pageable);
    }
}
