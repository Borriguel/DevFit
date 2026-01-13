package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.GymEquipmentMapper;
import dev.borriguel.devfit.model.Category;
import dev.borriguel.devfit.model.dtos.GymEquipmentRequestDto;
import dev.borriguel.devfit.model.dtos.GymEquipmentResponseDto;
import dev.borriguel.devfit.service.GymEquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym-equipment")
@RequiredArgsConstructor
public class GymEquipmentController {
    private final GymEquipmentService service;
    private final GymEquipmentMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GymEquipmentResponseDto createGymEquipment(@RequestBody @Valid GymEquipmentRequestDto dto) {
        var equipment = service.createGymEquipment(mapper.toGymEquipment(dto));
        return mapper.toGymEquipmentResponseDto(equipment);
    }

    @GetMapping("/{id}")
    public GymEquipmentResponseDto getById(@PathVariable Long id) {
        return mapper.toGymEquipmentResponseDto(service.getById(id));
    }

    @GetMapping
    public List<GymEquipmentResponseDto> getAll() {
        return mapper.toGymEquipmentResponseDtoList(service.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public GymEquipmentResponseDto updateById(@PathVariable Long id, @RequestBody @Valid GymEquipmentRequestDto dto) {
        var updatedEquipment = service.updateById(id, mapper.toGymEquipment(dto));
        return mapper.toGymEquipmentResponseDto(updatedEquipment);
    }

    @GetMapping("/category/{category}")
    public List<GymEquipmentResponseDto> getAllByCategory(@PathVariable Category category) {
        return mapper.toGymEquipmentResponseDtoList(service.getAllByCategory(category));
    }
}
