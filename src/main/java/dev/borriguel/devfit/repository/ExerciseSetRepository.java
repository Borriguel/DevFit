package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.ExerciseSet;
import dev.borriguel.devfit.model.dtos.ExerciseSetResponseDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    @Query("SELECT new dev.borriguel.devfit.model.dtos.ExerciseSetResponseDto(e.id, e.reps, e.sets, e.equipment.id, null) FROM ExerciseSet e WHERE e.session.id = :id")
    List<ExerciseSetResponseDto> findBySession_Id(Long id);
    @EntityGraph(attributePaths = {"equipment"})
    @Query("SELECT e FROM ExerciseSet e WHERE e.id = :id")
    Optional<ExerciseSet> findByIdWithEquipment(Long id);
}