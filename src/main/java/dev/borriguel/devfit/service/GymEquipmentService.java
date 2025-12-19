package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.Category;
import dev.borriguel.devfit.model.GymEquipment;
import dev.borriguel.devfit.repository.GymEquipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymEquipmentService {
    private final GymEquipmentRepository repository;

    @Transactional
    public GymEquipment createGymEquipment(GymEquipment gym) {
        return repository.save(gym);
    }

    public GymEquipment getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Gym equipment not found with id: " + id + ""));
    }

    @Transactional
    public void deleteById(Long id) {
        var gymEquipment = getById(id);
        repository.delete(gymEquipment);
    }

    public List<GymEquipment> getAll() {
        return repository.findAll();
    }

    public List<GymEquipment> getAllByCategory(Category category) {
        return repository.findByCategory(category);
    }

    @Transactional
    public GymEquipment updateById(Long id, GymEquipment equipmentUpdated) {
        var equipmentToUpdate = getById(id);
        equipmentToUpdate.updateName(equipmentUpdated.getName());
        equipmentToUpdate.updateDescription(equipmentUpdated.getDescription());
        equipmentToUpdate.updateImage(equipmentUpdated.getImageUrl());
        equipmentToUpdate.updateCategory(equipmentUpdated.getCategory());
        return repository.save(equipmentToUpdate);
    }
}
