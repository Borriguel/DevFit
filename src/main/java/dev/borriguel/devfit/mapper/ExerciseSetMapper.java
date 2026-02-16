package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.ExerciseSet;
import dev.borriguel.devfit.model.dtos.ExerciseSetResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseSetMapper {
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "equipment", ignore = true)
    ExerciseSetResponseDto toSimpleDto(ExerciseSet exerciseSet);
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "equipment", source = "equipment")
    ExerciseSetResponseDto toExpandedDto(ExerciseSet exerciseSet);
}
