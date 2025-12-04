package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.WorkoutLog;
import dev.borriguel.devfit.model.dtos.WorkoutLogResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutLogMapper {
    @Mapping(target = "sessionId", source = "session.id")
    @Mapping(target = "session", ignore = true)
    WorkoutLogResponseDto toSimpleDto(WorkoutLog workoutLog);

    @Mapping(target = "sessionId", source = "session.id")
    WorkoutLogResponseDto toExpandDto(WorkoutLog workoutLog);
}
