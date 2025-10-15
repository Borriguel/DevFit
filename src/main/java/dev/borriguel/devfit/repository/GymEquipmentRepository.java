package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.GymEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymEquipmentRepository extends JpaRepository<GymEquipment, Long> {
}