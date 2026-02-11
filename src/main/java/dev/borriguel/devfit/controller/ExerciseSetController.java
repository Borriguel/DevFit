package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.ExerciseSetMapper;
import dev.borriguel.devfit.model.dtos.ExerciseSetRequestDto;
import dev.borriguel.devfit.model.dtos.ExerciseSetResponseDto;
import dev.borriguel.devfit.service.ExerciseSetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise-set")
@RequiredArgsConstructor
public class ExerciseSetController {
    private final ExerciseSetService service;
    private final ExerciseSetMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    public ExerciseSetResponseDto createExerciseSet(@RequestBody @Valid ExerciseSetRequestDto dto) {
        return mapper.toSimpleDto(service.createExerciseSet(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER', 'MEMBER')")
    public ExerciseSetResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandEquipment = "equipment".equalsIgnoreCase(expand);
        if (expandEquipment) return mapper.toExpandedDto(service.getByIdWithEquipment(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/training-session/{trainingSessionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER', 'MEMBER')")
    public List<ExerciseSetResponseDto> getAllByTrainingSessionId(@PathVariable Long trainingSessionId) {
        return mapper.toSimpleDtoList(service.getAllByTrainingSession(trainingSessionId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
