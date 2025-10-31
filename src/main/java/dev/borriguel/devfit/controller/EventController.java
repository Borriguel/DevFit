package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.EventMapper;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import dev.borriguel.devfit.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;
    private final EventMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDto createEvent(@RequestBody EventRequestDto dto) {
        var event = service.createEvent(mapper.toEvent(dto), dto.gymUnitId());
        return mapper.toEventResponseDto(event);
    }

    @GetMapping("/{id}")
    public EventResponseDto getById(@PathVariable Long id) {
        return mapper.toEventResponseDto(service.getById(id));
    }

    @GetMapping
    public Page<EventResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @GetMapping("/gym/{gymId}")
    public Page<EventResponseDto> getAllByGymId(@PathVariable Long gymId, @ParameterObject Pageable page) {
        return service.getAllByGymIdAsDto(gymId, page);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
