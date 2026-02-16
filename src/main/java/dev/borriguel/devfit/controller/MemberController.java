package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.MemberMapper;
import dev.borriguel.devfit.model.Goal;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import dev.borriguel.devfit.model.dtos.MemberUpdateMetricsDto;
import dev.borriguel.devfit.service.MemberService;
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
public class MemberController {
    private final MemberService service;
    private final MemberMapper mapper;

    @GetMapping
    public Page<MemberResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @GetMapping("/unit/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PERSONAL_TRAINER')")
    public Page<MemberResponseDto> getAllByUnitId(@PathVariable Long unitId, @ParameterObject Pageable page) {
        return service.getAllByUnitIdAsDto(unitId, page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') and #id.equals(principal.profileId) or hasAnyRole('ADMIN', 'MANAGER', 'PERSONAL_TRAINER')")
    public MemberResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandedDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @PatchMapping("/update-metrics/{id}")
    @PreAuthorize("hasRole('MEMBER') and #id.equals(principal.profileId)")
    public MemberResponseDto updateMetrics(@PathVariable Long id, @RequestBody @Valid MemberUpdateMetricsDto dto) {
        var member = service.updateMetrics(id, dto.weight(), dto.height());
        return mapper.toSimpleDto(member);
    }

    @PatchMapping("/goal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MEMBER') and #id.equals(principal.profileId)")
    public void updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        service.updateGoal(id, goal);
    }
}