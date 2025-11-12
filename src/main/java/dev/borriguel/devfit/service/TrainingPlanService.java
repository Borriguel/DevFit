package dev.borriguel.devfit.service;

import dev.borriguel.devfit.model.Goal;
import dev.borriguel.devfit.model.TrainingPlan;
import dev.borriguel.devfit.repository.TrainingPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {
    private final TrainingPlanRepository repository;
    private final PersonalTrainerService personalService;
    private final MemberService memberService;

    @Transactional
    public TrainingPlan createTrainingPlan(String title, Goal goal, Long personalId, Long memberId) {
        var personal = personalService.getById(personalId);
        var member = memberService.getById(memberId);
        var trainingPlan = new TrainingPlan(title, goal, personal, member);
        return repository.save(trainingPlan);
    }

    public TrainingPlan getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Training plan not found"));
    }

    public List<TrainingPlan> getAllByMemberId(Long memberId) {
        return repository.findByMember_Id(memberId);
    }

    public List<TrainingPlan> getAllByPersonalId(Long personalId) {
        return repository.findByPersonal_Id(personalId);
    }

    @Transactional
    public TrainingPlan updateById(Long id, String title, Goal goal, LocalDate endDate) {
        var trainingPlanToUpdate = getById(id);
        trainingPlanToUpdate.updateTitle(title);
        trainingPlanToUpdate.updateGoal(goal);
        trainingPlanToUpdate.updateEndDate(endDate);
        return repository.save(trainingPlanToUpdate);
    }

    @Transactional
    public void deleteById(Long id) {
        var trainingPlan = getById(id);
        repository.delete(trainingPlan);
    }
}
