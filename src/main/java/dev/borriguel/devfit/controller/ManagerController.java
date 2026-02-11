package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.mapper.ManagerMapper;
import dev.borriguel.devfit.model.dtos.ManagerResponseDto;
import dev.borriguel.devfit.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService service;
    private final ManagerMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("#id.equals(principal.profileId) and hasRole('MANAGER') or hasRole('ADMIN')")
    public ManagerResponseDto getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandUnit = expand != null && expand.contains("unit");
        if (expandUnit) return mapper.toExpandedDto(service.getByIdWithUnit(id));
        return mapper.toSimpleDto(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ManagerResponseDto> getAll(@ParameterObject Pageable page) {
        return service.getAllAsDto(page);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#id.equals(principal.profileId) and hasRole('MANAGER') or hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
