package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Category;
import dev.borriguel.devfit.model.GymEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymEquipmentRepository extends JpaRepository<GymEquipment, Long> {
    List<GymEquipment> findByCategory(Category category);
}