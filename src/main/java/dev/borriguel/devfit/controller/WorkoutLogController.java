package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.WorkoutLogMapper;
import dev.borriguel.devfit.model.dtos.WorkoutLogResponseDto;
import dev.borriguel.devfit.service.WorkoutLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout-log")
@RequiredArgsConstructor
@Tag(name = "Registro de Treinos", description = "Operações de gerenciamento de registros de treinos")
public class WorkoutLogController {
    private final WorkoutLogService service;
    private final WorkoutLogMapper mapper;

    @PostMapping("/{trainingSessionId}")
    @PreAuthorize("hasRole('MEMBER')")
    @Operation(summary = "Criar registro de treino", description = "Cria um novo registro de treino para uma sessão de treino")
    public WorkoutLogResponseDto createWorkoutLog(@PathVariable Long trainingSessionId) {
        return mapper.toSimpleDto(service.createWorkoutLog(trainingSessionId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro de treino por ID", description = "Retorna um registro de treino específico pelo ID. Pode expandir para incluir a sessão de treino usando o parâmetro 'expand'")
    public WorkoutLogResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandSession = expand != null && expand.contains("session");
        if (expandSession) return mapper.toExpandDto(service.getByIdWithSession(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MEMBER')")
    @Operation(summary = "Excluir registro de treino", description = "Exclui um registro de treino pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
