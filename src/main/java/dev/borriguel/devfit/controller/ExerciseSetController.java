package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.ExerciseSetMapper;
import dev.borriguel.devfit.model.dtos.ExerciseSetRequestDto;
import dev.borriguel.devfit.model.dtos.ExerciseSetResponseDto;
import dev.borriguel.devfit.service.ExerciseSetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise-set")
@RequiredArgsConstructor
@Tag(name = "Séries de Exercícios", description = "Operações de gerenciamento de séries de exercícios")
public class ExerciseSetController {
    private final ExerciseSetService service;
    private final ExerciseSetMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    @Operation(summary = "Criar série de exercício", description = "Cria uma nova série de exercício para um plano de treino")
    public ExerciseSetResponseDto createExerciseSet(@RequestBody @Valid ExerciseSetRequestDto dto) {
        return mapper.toSimpleDto(service.createExerciseSet(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER', 'MEMBER')")
    @Operation(summary = "Buscar série de exercício por ID", description = "Retorna uma série de exercício específica pelo ID. Pode expandir para incluir o equipamento usando o parâmetro 'expand'")
    public ExerciseSetResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandEquipment = "equipment".equalsIgnoreCase(expand);
        if (expandEquipment) return mapper.toExpandedDto(service.getByIdWithEquipment(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/training-session/{trainingSessionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER', 'MEMBER')")
    @Operation(summary = "Listar séries por sessão de treino", description = "Retorna todas as séries de exercícios de uma sessão de treino específica")
    public List<ExerciseSetResponseDto> getAllByTrainingSessionId(@PathVariable Long trainingSessionId) {
        return mapper.toSimpleDtoList(service.getAllByTrainingSession(trainingSessionId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    @Operation(summary = "Excluir série de exercício", description = "Exclui uma série de exercício pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
