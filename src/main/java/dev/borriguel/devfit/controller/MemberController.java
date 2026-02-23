package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.MemberMapper;
import dev.borriguel.devfit.model.Goal;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import dev.borriguel.devfit.model.dtos.MemberUpdateMetricsDto;
import dev.borriguel.devfit.service.MemberService;
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
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "Membros", description = "Operações de gerenciamento de membros da academia")
public class MemberController {
    private final MemberService service;
    private final MemberMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todos os membros", description = "Retorna uma paginação de todos os membros da academia")
    public Page<MemberResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @GetMapping("/unit/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PERSONAL_TRAINER')")
    @Operation(summary = "Listar membros por unidade", description = "Retorna uma paginação de membros de uma unidade específica da academia")
    public Page<MemberResponseDto> getAllByUnitId(@PathVariable Long unitId, @ParameterObject Pageable page) {
        return service.getAllByUnitIdAsDto(unitId, page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') and #id.equals(principal.profileId) or hasAnyRole('ADMIN', 'MANAGER', 'PERSONAL_TRAINER')")
    @Operation(summary = "Buscar membro por ID", description = "Retorna um membro específico pelo ID. Pode expandir para incluir a unidade usando o parâmetro 'expand'")
    public MemberResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandedDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @PatchMapping("/update-metrics/{id}")
    @PreAuthorize("hasRole('MEMBER') and #id.equals(principal.profileId)")
    @Operation(summary = "Atualizar métricas do membro", description = "Atualiza o peso e a altura de um membro")
    public MemberResponseDto updateMetrics(@PathVariable Long id, @RequestBody @Valid MemberUpdateMetricsDto dto) {
        var member = service.updateMetrics(id, dto.weight(), dto.height());
        return mapper.toSimpleDto(member);
    }

    @PatchMapping("/goal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MEMBER') and #id.equals(principal.profileId)")
    @Operation(summary = "Atualizar objetivo do membro", description = "Atualiza o objetivo de treino de um membro")
    public void updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        service.updateGoal(id, goal);
    }
}