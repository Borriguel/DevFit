package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.TrainingPlan;
import dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
    @Query("SELECT new dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto(t.id, t.title, t.goal, t.startDate, t.endDate, t.personal.id, null, t.member.id, null) FROM TrainingPlan t WHERE t.member.id =:id")
    List<TrainingPlanResponseDto> findByMember_IdAsDto(Long id);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto(t.id, t.title, t.goal, t.startDate, t.endDate, t.personal.id, null, t.member.id, null) FROM TrainingPlan t WHERE t.personal.id =:id")
    Page<TrainingPlanResponseDto> findByPersonal_IdAsDto(Long id, Pageable pageable);
    
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