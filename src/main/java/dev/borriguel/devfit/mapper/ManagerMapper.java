package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Manager;
import dev.borriguel.devfit.model.dtos.ManagerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    @Named("simpleMapping")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unit", ignore = true)
    ManagerResponseDto toSimpleDto(Manager manager);

    @Mapping(target = "unitId", source = "unit.id")
    ManagerResponseDto toExpandedDto(Manager manager);
}
