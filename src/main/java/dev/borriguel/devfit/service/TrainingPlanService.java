package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.TrainingPlan;
import dev.borriguel.devfit.model.dtos.TrainingPlanRequestDto;
import dev.borriguel.devfit.model.dtos.TrainingPlanUpdateRequestDto;
import dev.borriguel.devfit.repository.TrainingPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {
    private final TrainingPlanRepository repository;
    private final PersonalTrainerService personalService;
    private final MemberService memberService;

    @Transactional
    public TrainingPlan createTrainingPlan(TrainingPlanRequestDto dto) {
        var personal = personalService.getById(dto.personalTrainerId());
        var member = memberService.getById(dto.memberId());
        var trainingPlan = new TrainingPlan(dto.title(), dto.goal(), personal, member);
        return repository.save(trainingPlan);
    }

    public TrainingPlan getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Training plan not found with id: " + id));
    }

    public TrainingPlan getByIdWithPersonal(Long id) {
        return repository.findByIdWithPersonal(id).orElseThrow(() -> new ResourceNotFound("Training plan not found with id: " + id));
    }

    public TrainingPlan getByIdWithMember(Long id) {
        return repository.findByIdWithMember(id).orElseThrow(() -> new ResourceNotFound("Training plan not found with id: " + id));
    }

    public TrainingPlan getByIdWithPersonalAndMember(Long id) {
        return repository.findByIdWithPersonalAndMember(id).orElseThrow(() -> new ResourceNotFound("Training plan not found with id: " + id));
    }

    public List<TrainingPlan> getAllByMemberId(Long memberId) {
        return repository.findByMember_Id(memberId);
    }

    public List<TrainingPlan> getAllByPersonalId(Long personalId) {
        return repository.findByPersonal_Id(personalId);
    }

    @Transactional
    public TrainingPlan updateById(Long id, TrainingPlanUpdateRequestDto dto) {
        var trainingPlanToUpdate = getById(id);
        trainingPlanToUpdate.updateTitle(dto.title());
        trainingPlanToUpdate.updateGoal(dto.goal());
        trainingPlanToUpdate.updateEndDate(dto.endDate());
        return repository.save(trainingPlanToUpdate);
    }

    @Transactional
    public void deleteById(Long id) {
        var trainingPlan = getById(id);
        repository.delete(trainingPlan);
    }
}
