package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.TrainingSessionMapper;
import dev.borriguel.devfit.model.dtos.TrainingSessionResponseDto;
import dev.borriguel.devfit.service.TrainingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-session")
@RequiredArgsConstructor
@Tag(name = "Sessões de Treino", description = "Operações de gerenciamento de sessões de treino")
public class TrainingSessionController {
    private final TrainingSessionService service;
    private final TrainingSessionMapper mapper;

    @PostMapping("/{trainingPlanId}/{personalId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    @Operation(summary = "Criar sessão de treino", description = "Cria uma nova sessão de treino para um plano de treino")
    public TrainingSessionResponseDto createTrainingSession(@PathVariable Long trainingPlanId, @PathVariable Long personalId) {
        return mapper.toSimpleDto(service.createTrainingSession(trainingPlanId, personalId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão de treino por ID", description = "Retorna uma sessão de treino específica pelo ID. Pode expandir para incluir plano de treino e/ou exercícios usando o parâmetro 'expand'")
    public TrainingSessionResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandTrainingPlan = expand != null && expand.contains("training-plan");
        boolean expandExercises = expand != null && expand.contains("exercises");
        if (expandTrainingPlan && expandExercises) return mapper.toExpandDto(service.getByIdWithTrainingPlanAndExercises(id));
        if (expandTrainingPlan) return mapper.toExpandWithTrainingPlanOnly(service.getByIdWithTrainingPlan(id));
        if (expandExercises) return mapper.toExpandWithExercisesOnly(service.getByIdWithExercises(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/training-session/{trainingSessionId}")
    @Operation(summary = "Listar sessões por plano de treino", description = "Retorna todas as sessões de treino de um plano de treino específico")
    public List<TrainingSessionResponseDto> getAllByTrainingSessionId(@PathVariable Long trainingSessionId) {
        return service.getAllByTrainingPlanAsDto(trainingSessionId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    @Operation(summary = "Excluir sessão de treino", description = "Exclui uma sessão de treino pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
