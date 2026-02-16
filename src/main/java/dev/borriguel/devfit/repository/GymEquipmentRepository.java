package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Category;
import dev.borriguel.devfit.model.GymEquipment;
import dev.borriguel.devfit.model.dtos.GymEquipmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GymEquipmentRepository extends JpaRepository<GymEquipment, Long> {
    @Query("SELECT new dev.borriguel.devfit.model.dtos.GymEquipmentResponseDto(e.id, e.name, e.description, e.imageUrl, e.category) FROM GymEquipment e WHERE e.category = :category")
    Page<GymEquipmentResponseDto> findAllByCategoryAsDto(Category category, Pageable pageable);
    @Query("SELECT new dev.borriguel.devfit.model.dtos.GymEquipmentResponseDto(e.id, e.name, e.description, e.imageUrl, e.category) FROM GymEquipment e")
    Page<GymEquipmentResponseDto> findAllAsDto(Pageable pageable);
}