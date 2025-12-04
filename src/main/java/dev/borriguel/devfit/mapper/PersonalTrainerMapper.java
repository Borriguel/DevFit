package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.PersonalTrainer;
import dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonalTrainerMapper {
    @Named("simpleMapping")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unit", ignore = true)
    PersonalTrainerResponseDto toSimpleDto(PersonalTrainer personalTrainer);

    @Mapping(target = "unitId", source = "unit.id")
    PersonalTrainerResponseDto toExpandDto(PersonalTrainer personalTrainer);

    @IterableMapping(qualifiedByName = "simpleMapping")
    List<PersonalTrainerResponseDto> toSimpleDtoList(List<PersonalTrainer> personalTrainers);
}
