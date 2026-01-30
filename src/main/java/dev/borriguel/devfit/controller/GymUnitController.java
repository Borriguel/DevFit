package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.GymUnitMapper;
import dev.borriguel.devfit.model.dtos.GymUnitRequestDto;
import dev.borriguel.devfit.model.dtos.GymUnitResponseDto;
import dev.borriguel.devfit.service.GymUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/gym-units")
@RequiredArgsConstructor
public class GymUnitController {
    private final GymUnitService service;
    private final GymUnitMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GymUnitResponseDto createGymUnit(@RequestBody @Valid GymUnitRequestDto dto) {
        var gym = service.createGymUnit(mapper.toGymUnit(dto));
        return mapper.toGymUnitResponseDto(gym);
    }

    @GetMapping("/{id}")
    public GymUnitResponseDto getById(@PathVariable Long id) {
        return mapper.toGymUnitResponseDto(service.getById(id));
    }

    @PatchMapping("/update-monthly-fee/{id}")
    public GymUnitResponseDto updateMonthlyFeeById(@PathVariable Long id, @RequestBody BigDecimal newMonthlyFee) {
        var updatedGymUnit = service.updateMonthlyFeeById(id, newMonthlyFee);
        return mapper.toGymUnitResponseDto(updatedGymUnit);
    }

    @PutMapping("/{id}")
    public GymUnitResponseDto updateById(@PathVariable Long id, @RequestBody @Valid GymUnitRequestDto dto) {
        var updatedGymUnit = service.updateById(id, mapper.toGymUnit(dto));
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

    @PutMapping("/{destinationUnitId}/members/{memberId}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reassignMemberUnit(@PathVariable Long memberId, @PathVariable Long destinationUnitId) {
        service.transferMember(memberId, destinationUnitId);
    }

    @PutMapping("/{destinationUnitId}/personal-trainers/{trainerId}/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reassignPersonalUnit(@PathVariable Long trainerId, @PathVariable Long destinationUnitId) {
        service.transferPersonal(trainerId, destinationUnitId);
    }

    @DeleteMapping("/managers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeManager(@PathVariable Long id) {
        service.removeManager(id);
    }

    @PatchMapping("{destinationUnitId}/managers/{managerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateManager(@PathVariable Long destinationUnitId, @PathVariable Long managerId) {
        service.assignManager(destinationUnitId, managerId);
    }
}
