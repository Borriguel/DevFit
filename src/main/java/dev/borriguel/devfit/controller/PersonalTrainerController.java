package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.PersonalTrainerMapper;
import dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto;
import dev.borriguel.devfit.service.PersonalTrainerService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal-trainer")
@RequiredArgsConstructor
public class PersonalTrainerController {
    private final PersonalTrainerService service;
    private final PersonalTrainerMapper mapper;

    @GetMapping("/{id}")
    public PersonalTrainerResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping("/unit/{unitId}")
    public Page<PersonalTrainerResponseDto> getAllByUnitId(@PathVariable Long unitId, @ParameterObject Pageable page) {
        return service.getAllByUnitIdAsDto(unitId, page);
    }
}
