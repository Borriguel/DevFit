package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.GymUnitMapper;
import dev.borriguel.devfit.model.dtos.GymUnitRequestDto;
import dev.borriguel.devfit.model.dtos.GymUnitResponseDto;
import dev.borriguel.devfit.service.GymUnitService;
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

import java.math.BigDecimal;

@RestController
@RequestMapping("/gym-units")
@RequiredArgsConstructor
@Tag(name = "Unidades de Academia", description = "Operações de gerenciamento de unidades da academia")
public class GymUnitController {
    private final GymUnitService service;
    private final GymUnitMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar unidade", description = "Cria uma nova unidade da academia")
    public GymUnitResponseDto createGymUnit(@RequestBody @Valid GymUnitRequestDto dto) {
        var gym = service.createGymUnit(mapper.toGymUnit(dto));
        return mapper.toGymUnitResponseDto(gym);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar unidade por ID", description = "Retorna uma unidade da academia específica pelo ID")
    public GymUnitResponseDto getById(@PathVariable Long id) {
        return mapper.toGymUnitResponseDto(service.getById(id));
    }

    @PatchMapping("/update-monthly-fee/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Atualizar mensalidade da unidade", description = "Atualiza o valor da mensalidade de uma unidade da academia")
    public GymUnitResponseDto updateMonthlyFeeById(@PathVariable Long id, @RequestBody BigDecimal newMonthlyFee) {
        var updatedGymUnit = service.updateMonthlyFeeById(id, newMonthlyFee);
        return mapper.toGymUnitResponseDto(updatedGymUnit);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Atualizar unidade", description = "Atualiza uma unidade da academia existente pelo ID")
    public GymUnitResponseDto updateById(@PathVariable Long id, @RequestBody @Valid GymUnitRequestDto dto) {
        var updatedGymUnit = service.updateById(id, mapper.toGymUnit(dto));
        return mapper.toGymUnitResponseDto(updatedGymUnit);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Listar todas as unidades", description = "Retorna uma paginação de todas as unidades da academia")
    public Page<GymUnitResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAll(page);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir unidade", description = "Exclui uma unidade da academia pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{destinationUnitId}/members/{memberId}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Transferir membro para outra unidade", description = "Transfere um membro para uma unidade de destino")
    public void reassignMemberUnit(@PathVariable Long memberId, @PathVariable Long destinationUnitId) {
        service.transferMember(memberId, destinationUnitId);
    }

    @PutMapping("/{destinationUnitId}/personal-trainers/{trainerId}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Transferir personal trainer para outra unidade", description = "Transfere um personal trainer para uma unidade de destino")
    public void reassignPersonalUnit(@PathVariable Long trainerId, @PathVariable Long destinationUnitId) {
        service.transferPersonal(trainerId, destinationUnitId);
    }

    @DeleteMapping("/managers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover gerente da unidade", description = "Remove um gerente de uma unidade da academia")
    public void removeManager(@PathVariable Long id) {
        service.removeManager(id);
    }

    @PatchMapping("{destinationUnitId}/managers/{managerId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atribuir gerente à unidade", description = "Atribui um gerente a uma unidade da academia")
    public void updateManager(@PathVariable Long destinationUnitId, @PathVariable Long managerId) {
        service.assignManager(destinationUnitId, managerId);
    }
}
