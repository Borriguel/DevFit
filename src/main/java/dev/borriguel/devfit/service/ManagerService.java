package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.Manager;
import dev.borriguel.devfit.model.dtos.ManagerResponseDto;
import dev.borriguel.devfit.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository repository;

    public Manager getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Manager not found with id: " + id));
    }

    public Manager getByIdWithUnit(Long id) {
        return repository.findByIdWithUnit(id).orElseThrow(() -> new ResourceNotFound("Manager not found with id: " + id));
    }

    public Page<ManagerResponseDto> getAllAsDto(Pageable page) {
        return repository.findAllAsDto(page);
    }

    @Transactional
    public void deleteById(Long id) {
        var manager = getById(id);
        repository.delete(manager);
    }
}
