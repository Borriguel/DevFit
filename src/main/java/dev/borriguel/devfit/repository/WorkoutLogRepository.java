package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.WorkoutLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    @EntityGraph(attributePaths = {"session"})
    @Query("SELECT w FROM WorkoutLog w WHERE w.id = :id")
    Optional<WorkoutLog> findByIdWithSession(Long id);
}