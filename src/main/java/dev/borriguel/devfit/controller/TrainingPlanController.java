package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.TrainingPlanMapper;
import dev.borriguel.devfit.model.dtos.TrainingPlanRequestDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanUpdateRequestDto;
import dev.borriguel.devfit.service.TrainingPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    public TrainingPlanResponseDto createTrainingPlan(@RequestBody @Valid TrainingPlanRequestDto dto) {
        var trainingPlan = service.createTrainingPlan(dto);
        return mapper.toSimpleDto(trainingPlan);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PERSONAL_TRAINER') or hasRole('MEMBER') and @trainingPlanService.getById(#id).member.id.equals(principal.profileId)")
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
    public List<TrainingPlanResponseDto> getAllByMemberId(@PathVariable Long memberId) {
        return mapper.toSimpleDtoList(service.getAllByMemberId(memberId));
    }

    @GetMapping("/personal/{personalId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') and #personalId.equals(principal.profileId) or hasAnyRole('ADMIN')")
    public List<TrainingPlanResponseDto> getAllByPersonalId(@PathVariable Long personalId) {
        return  mapper.toSimpleDtoList(service.getAllByPersonalId(personalId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER')")
    public TrainingPlanResponseDto updateById(@PathVariable Long id, @RequestBody @Valid TrainingPlanUpdateRequestDto dto) {
        var trainingPlanUpdated = service.updateById(id, dto);
        return mapper.toSimpleDto(trainingPlanUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('PERSONAL_TRAINER', 'ADMIN') or hasRole('MEMBER') and @trainingPlanService.getById(#id).member.id.equals(principal.profileId)")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
