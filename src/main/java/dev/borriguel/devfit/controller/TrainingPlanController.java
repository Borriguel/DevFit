package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.TrainingPlanMapper;
import dev.borriguel.devfit.model.dtos.TrainingPlanRequestDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanUpdateRequestDto;
import dev.borriguel.devfit.service.TrainingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-plan")
@RequiredArgsConstructor
public class TrainingPlanController {
    private final TrainingPlanService service;
    private final TrainingPlanMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingPlanResponseDto createTrainingPlan(@RequestBody TrainingPlanRequestDto dto) {
        var trainingPlan = service.createTrainingPlan(dto.title(), dto.goal(), dto.personalTrainerId(), dto.memberId());
        return mapper.toTrainingPlanResponseDto(trainingPlan);
    }

    @GetMapping("/{id}")
    public TrainingPlanResponseDto getById(@PathVariable Long id) {
        return mapper.toTrainingPlanResponseDto(service.getById(id));
    }

    @GetMapping("/member/{memberId}")
    public List<TrainingPlanResponseDto> getAllByMemberId(@PathVariable Long memberId) {
        return mapper.toTrainingPlanResponseDtoList(service.getAllByMemberId(memberId));
    }

    @GetMapping("/personal/{personalId}")
    public List<TrainingPlanResponseDto> getAllByPersonalId(@PathVariable Long personalId) {
        return  mapper.toTrainingPlanResponseDtoList(service.getAllByPersonalId(personalId));
    }

    @PutMapping("/{id}")
    public TrainingPlanResponseDto updateById(@PathVariable Long id, @RequestBody TrainingPlanUpdateRequestDto dto) {
        var trainingPlanUpdated = service.updateById(id, dto.title(), dto.goal(), dto.endDate());
        return mapper.toTrainingPlanResponseDto(trainingPlanUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
