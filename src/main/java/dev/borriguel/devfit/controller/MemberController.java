package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.MemberMapper;
import dev.borriguel.devfit.model.Goal;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import dev.borriguel.devfit.model.dtos.MemberUpdateMetricsDto;
import dev.borriguel.devfit.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public MemberResponseDto getById(@PathVariable Long id) {
        return mapper.toMemberResponseDto(service.getById(id));
    }

    @PatchMapping("/updateMetrics/{id}")
    public MemberResponseDto updateMetrics(@PathVariable Long id, @RequestBody MemberUpdateMetricsDto dto) {
        var member = service.updateMetrics(id, dto.weight(), dto.height());
        return mapper.toMemberResponseDto(member);
    }

    @PatchMapping("/goal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        service.updateGoal(id, goal);
    }
}