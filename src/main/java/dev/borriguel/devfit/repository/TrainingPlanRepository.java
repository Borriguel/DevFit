package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.TrainingPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
    List<TrainingPlan> findByMember_Id(Long id);
    List<TrainingPlan> findByPersonal_Id(Long id);
    @EntityGraph(attributePaths = {"personal"})
    @Query("SELECT t FROM TrainingPlan t WHERE t.id = :id")
    Optional<TrainingPlan> findByIdWithPersonal(Long id);

    @EntityGraph(attributePaths = {"member"})
    @Query("SELECT t FROM TrainingPlan t WHERE t.id = :id")
    Optional<TrainingPlan> findByIdWithMember(Long id);

    @EntityGraph(attributePaths = {"personal", "member"})
    @Query("SELECT t FROM TrainingPlan t WHERE t.id = :id")
    Optional<TrainingPlan> findByIdWithPersonalAndMember(Long id);
}