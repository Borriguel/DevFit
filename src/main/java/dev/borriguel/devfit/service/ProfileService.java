package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.Profile;
import dev.borriguel.devfit.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;

    public Profile getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Profile not found with id: " + id));
    }

    public boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }
}
