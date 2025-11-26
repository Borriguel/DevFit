package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.ExerciseSet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    List<ExerciseSet> findBySession_Id(Long id);
    @EntityGraph(attributePaths = {"equipment"})
    @Query("SELECT e FROM ExerciseSet e WHERE e.id = :id")
    Optional<ExerciseSet> findByIdWithEquipment(Long id);
}