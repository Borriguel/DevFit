package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.TrainingPlan;
import dev.borriguel.devfit.model.dtos.TrainingPlanResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingPlanMapper {
    @Named("simpleMapping")
    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "personal", ignore = true)
    @Mapping(target = "member", ignore = true)
    TrainingPlanResponseDto toSimpleDto(TrainingPlan trainingPlan);

    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "memberId", source = "member.id")
    TrainingPlanResponseDto toExpandedDto(TrainingPlan trainingPlan);

    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "member", ignore = true)
    TrainingPlanResponseDto toExpandedWithPersonalOnly(TrainingPlan trainingPlan);

    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "personal", ignore = true)
    TrainingPlanResponseDto toExpandedWithMemberOnly(TrainingPlan trainingPlan);

    @IterableMapping(qualifiedByName = "simpleMapping")
    List<TrainingPlanResponseDto> toSimpleDtoList(List<TrainingPlan> trainingPlans);
}
