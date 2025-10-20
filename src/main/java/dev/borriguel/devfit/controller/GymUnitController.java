package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.GymUnitMapper;
import dev.borriguel.devfit.model.dtos.GymUnitRequestDto;
import dev.borriguel.devfit.model.dtos.GymUnitResponseDto;
import dev.borriguel.devfit.service.GymUnitService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/gym")
@RequiredArgsConstructor
public class GymUnitController {
    private final GymUnitService service;
    private final GymUnitMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GymUnitResponseDto createGymUnit(@RequestBody GymUnitRequestDto dto) {
        var gym = service.createGymUnit(mapper.toGymUnit(dto));
        return mapper.toGymUnitResponseDto(gym);
    }

    @GetMapping("/{id}")
    public GymUnitResponseDto getById(@PathVariable Long id) {
        return mapper.toGymUnitResponseDto(service.getById(id));
    }

    @PatchMapping("/updateMonthlyFee/{id}")
    public GymUnitResponseDto updateMonthlyFeeById(@PathVariable Long id, @RequestBody BigDecimal newMonthlyFee) {
        var updatedGymUnit = service.updateMonthlyFeeById(id, newMonthlyFee);
        return mapper.toGymUnitResponseDto(updatedGymUnit);
    }

    @GetMapping
    public Page<GymUnitResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
