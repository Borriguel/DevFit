package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.ExerciseSet;
import dev.borriguel.devfit.model.dtos.ExerciseSetResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseSetMapper {
    @Named("simpleMapping")
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "equipment", ignore = true)
    ExerciseSetResponseDto toSimpleDto(ExerciseSet exerciseSet);
    @IterableMapping(qualifiedByName = "simpleMapping")
    List<ExerciseSetResponseDto> toSimpleDtoList(List<ExerciseSet> exerciseSets);
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "equipment", source = "equipment")
    ExerciseSetResponseDto toExpandedDto(ExerciseSet exerciseSet);
}
