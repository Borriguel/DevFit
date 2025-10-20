package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.GymUnit;
import dev.borriguel.devfit.model.dtos.GymUnitRequestDto;
import dev.borriguel.devfit.model.dtos.GymUnitResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GymUnitMapper {
    GymUnit toGymUnit(GymUnitRequestDto dto);
    GymUnitResponseDto toGymUnitResponseDto(GymUnit gymUnit);
    List<GymUnitResponseDto> toGymUnitResponseDtoPage(List<GymUnit> gymUnits);
    @ObjectFactory
    default GymUnit gymUnitDefaultFactory(GymUnitRequestDto dto) {
        if ( dto == null ) return null;
        return new GymUnit(dto.name(), dto.address(), dto.monthlyFee());
    }
}
