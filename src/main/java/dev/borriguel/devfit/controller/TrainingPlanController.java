package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.TrainingPlanMapper;
import dev.borriguel.devfit.model.dtos.TrainingPlanRequestDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanUpdateRequestDto;
import dev.borriguel.devfit.service.TrainingPlanService;
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

import java.util.List;

@RestController
@RequestMapping("/training-plan")
@RequiredArgsConstructor
@Tag(name = "Planos de Treino", description = "Operações de gerenciamento de planos de treino")
public class TrainingPlanController {
    private final TrainingPlanService service;
    private final TrainingPlanMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    @Operation(summary = "Criar plano de treino", description = "Cria um novo plano de treino para um membro")
    public TrainingPlanResponseDto createTrainingPlan(@RequestBody @Valid TrainingPlanRequestDto dto) {
        var trainingPlan = service.createTrainingPlan(dto);
        return mapper.toSimpleDto(trainingPlan);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER') or hasRole('MEMBER') and @trainingPlanService.getById(#id).member.id.equals(principal.profileId)")
    @Operation(summary = "Buscar plano de treino por ID", description = "Retorna um plano de treino específico pelo ID. Pode expandir para incluir personal trainer e/ou membro usando o parâmetro 'expand'")
    public TrainingPlanResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandPersonal = expand != null && expand.contains("personal");
        boolean expandMember = expand != null && expand.contains("member");
        if (expandPersonal && expandMember) return mapper.toExpandedDto(service.getByIdWithPersonalAndMember(id));
        if (expandPersonal) return mapper.toExpandedWithPersonalOnly(service.getByIdWithPersonal(id));
        if (expandMember) return mapper.toExpandedWithMemberOnly(service.getByIdWithMember(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('MEMBER') and #memberId.equals(principal.profileId) or hasAnyRole('PERSONAL_TRAINER', 'ADMIN')")
    @Operation(summary = "Listar planos de treino por membro", description = "Retorna todos os planos de treino de um membro específico")
    public List<TrainingPlanResponseDto> getAllByMemberId(@PathVariable Long memberId) {
        return service.getAllByMemberIdAsDto(memberId);
    }

    @GetMapping("/personal/{personalId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') and #personalId.equals(principal.profileId) or hasAnyRole('ADMIN')")
    @Operation(summary = "Listar planos de treino por personal trainer", description = "Retorna uma paginação de planos de treino de um personal trainer específico")
    public Page<TrainingPlanResponseDto> getAllByPersonalId(@PathVariable Long personalId, @ParameterObject Pageable page) {
        return service.getAllByPersonalIdAsDto(personalId, page);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    @Operation(summary = "Atualizar plano de treino", description = "Atualiza um plano de treino existente pelo ID")
    public TrainingPlanResponseDto updateById(@PathVariable Long id, @RequestBody @Valid TrainingPlanUpdateRequestDto dto) {
        var trainingPlanUpdated = service.updateById(id, dto);
        return mapper.toSimpleDto(trainingPlanUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('PERSONAL_TRAINER', 'ADMIN') or hasRole('MEMBER') and @trainingPlanService.getById(#id).member.id.equals(principal.profileId)")
    @Operation(summary = "Excluir plano de treino", description = "Exclui um plano de treino pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
