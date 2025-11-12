package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
    List<TrainingPlan> findByMember_Id(Long id);
    List<TrainingPlan> findByPersonal_Id(Long id);
}