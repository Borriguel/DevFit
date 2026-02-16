package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.PersonalTrainer;
import dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {
    @EntityGraph(attributePaths = {"unit"})
    @Query("SELECT p FROM PersonalTrainer p WHERE p.id = :id")
    Optional<PersonalTrainer> findByIdWithUnit(Long id);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto(p.id, p.name, p.unit.id, null) FROM PersonalTrainer p WHERE p.unit.id =:id")
    Page<PersonalTrainerResponseDto> findByUnit_IdAsDto(Long id, Pageable pageable);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto(p.id, p.name, p.unit.id, null) FROM PersonalTrainer p")
    Page<PersonalTrainerResponseDto> findAllAsDto(Pageable pageable);
}