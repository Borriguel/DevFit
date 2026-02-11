package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.PersonalTrainerMapper;
import dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto;
import dev.borriguel.devfit.service.PersonalTrainerService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal-trainer")
@RequiredArgsConstructor
public class PersonalTrainerController {
    private final PersonalTrainerService service;
    private final PersonalTrainerMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasRole('PERSONAL_TRAINER') and #id == principal.profileId")
    public PersonalTrainerResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/unit/{unitId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Page<PersonalTrainerResponseDto> getAllByUnitId(@PathVariable Long unitId, @ParameterObject Pageable page) {
        return service.getAllByUnitIdAsDto(unitId, page);
    }
}
