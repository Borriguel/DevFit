package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.TrainingSession;
import dev.borriguel.devfit.model.dtos.TrainingSessionResponseDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    @Query("SELECT new dev.borriguel.devfit.model.dtos.TrainingSessionResponseDto(t.id, t.trainingPlan.id, null, SIZE(t.exercises), null) FROM TrainingSession t WHERE t.trainingPlan.id =:id")
    List<TrainingSessionResponseDto> findByTrainingPlan_IdAsDto(Long id);

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