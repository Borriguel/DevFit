package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.TrainingSession;
import dev.borriguel.devfit.model.dtos.TrainingSessionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingSessionMapper {
    @Mapping(target = "trainingPlanId", source = "trainingPlan.id")
    @Mapping(target = "trainingPlan", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    @Mapping(target = "totalExercises", source = "numberOfExercises")
    TrainingSessionResponseDto toSimpleDto(TrainingSession trainingSession);

    @Mapping(target = "trainingPlanId", source = "trainingPlan.id")
    @Mapping(target = "totalExercises", source = "numberOfExercises")
    TrainingSessionResponseDto toExpandDto(TrainingSession trainingSession);

    @Mapping(target = "trainingPlanId", source = "trainingPlan.id")
    @Mapping(target = "totalExercises", source = "numberOfExercises")
    @Mapping(target = "exercises", ignore = true)
    TrainingSessionResponseDto toExpandWithTrainingPlanOnly(TrainingSession trainingSession);

    @Mapping(target = "trainingPlanId", source = "trainingPlan.id")
    @Mapping(target = "trainingPlan", ignore = true)
    @Mapping(target = "totalExercises", source = "numberOfExercises")
    TrainingSessionResponseDto toExpandWithExercisesOnly(TrainingSession trainingSession);
}
