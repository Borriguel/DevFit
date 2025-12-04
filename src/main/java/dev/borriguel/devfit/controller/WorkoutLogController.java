package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.WorkoutLogMapper;
import dev.borriguel.devfit.model.dtos.WorkoutLogResponseDto;
import dev.borriguel.devfit.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout-log")
@RequiredArgsConstructor
public class WorkoutLogController {
    private final WorkoutLogService service;
    private final WorkoutLogMapper mapper;

    @PostMapping("/{trainingSessionId}")
    public WorkoutLogResponseDto createWorkoutLog(@PathVariable Long trainingSessionId) {
        return mapper.toSimpleDto(service.createWorkoutLog(trainingSessionId));
    }

    @GetMapping("/{id}")
    public WorkoutLogResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandSession = expand != null && expand.contains("session");
        if (expandSession) return mapper.toExpandDto(service.getByIdWithSession(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
