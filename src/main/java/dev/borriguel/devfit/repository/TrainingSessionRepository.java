package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.TrainingSession;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByTrainingPlan_Id(Long id);

    @EntityGraph(attributePaths = {"trainingPlan"})
    @Query("SELECT t FROM TrainingSession t WHERE t.id = :id")
    Optional<TrainingSession> findByIdWithTrainingPlan(Long id);

    @EntityGraph(attributePaths = {"exercises"})
    @Query("SELECT t FROM TrainingSession t WHERE t.id = :id")
    Optional<TrainingSession> findByIdWithExercises(Long id);

    @EntityGraph(attributePaths = {"trainingPlan", "exercises"})
    @Query("SELECT t FROM TrainingSession t WHERE t.id = :id")
    Optional<TrainingSession> findByIdWithTrainingPlanAndExercises(Long id);
}