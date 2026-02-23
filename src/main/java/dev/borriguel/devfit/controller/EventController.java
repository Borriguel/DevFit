package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.EventMapper;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import dev.borriguel.devfit.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Operações de gerenciamento de eventos da academia")
public class EventController {
    private final EventService service;
    private final EventMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Criar evento", description = "Cria um novo evento na academia")
    public EventResponseDto createEvent(@RequestBody @Valid EventRequestDto dto) {
        var event = service.createEvent(dto);
        return mapper.toSimpleDto(event);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID", description = "Retorna um evento específico pelo ID. Pode expandir para incluir participantes e/ou unidade da academia usando o parâmetro 'expand'")
    public EventResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandAttendees = expand != null && expand.contains("attendees");
        boolean expandGym = expand != null && expand.contains("unit");
        if (expandAttendees && expandGym) return mapper.toExpandedDto(service.getByIdWithGymUnitAndAttendees(id));
        if (expandAttendees) return mapper.toExpandedDtoWithAttendeesOnly(service.getByIdWithAttendees(id));
        if (expandGym) return mapper.toExpandDtoWithGymUnitOnly(service.getByIdWithGymUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os eventos", description = "Retorna uma paginação de todos os eventos")
    public Page<EventResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @GetMapping("/gym/{gymId}")
    @Operation(summary = "Listar eventos por unidade", description = "Retorna uma paginação de eventos de uma unidade da academia específica")
    public Page<EventResponseDto> getAllByGymId(@PathVariable Long gymId, @ParameterObject Pageable page) {
        return service.getAllByGymIdAsDto(gymId, page);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Excluir evento", description = "Exclui um evento pelo ID")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Atualizar evento", description = "Atualiza um evento existente pelo ID")
    public EventResponseDto updateById(@PathVariable Long id, @RequestBody @Valid EventRequestDto dto) {
        var eventUpdated = mapper.toEvent(dto);
        return mapper.toSimpleDto(service.updateById(id, eventUpdated));
    }

    @PostMapping("/{id}/join/profile/{profileId}")
    @PreAuthorize("hasRole('MEMBER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Participar de evento", description = "Inscreve um membro em um evento")
    public void joinEvent(@PathVariable Long id, @PathVariable Long profileId) {
        service.joinEvent(id, profileId);
    }

    @PostMapping("/{id}/leave/profile/{profileId}")
    @PreAuthorize("hasRole('MEMBER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sair de evento", description = "Remove a inscrição de um membro em um evento")
    public void leaveEvent(@PathVariable Long id, @PathVariable Long profileId) {
        service.leaveEvent(id, profileId);
    }
}
