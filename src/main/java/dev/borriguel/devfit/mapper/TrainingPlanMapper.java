package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.TrainingPlan;
import dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingPlanMapper {
    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "memberId", source = "member.id")
    TrainingPlanResponseDto toTrainingPlanResponseDto(TrainingPlan trainingPlan);
    List<TrainingPlanResponseDto> toTrainingPlanResponseDtoList(List<TrainingPlan> trainingPlans);
}
