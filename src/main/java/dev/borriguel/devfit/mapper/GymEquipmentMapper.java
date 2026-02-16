package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.GymEquipment;
import dev.borriguel.devfit.model.dtos.GymEquipmentRequestDto;
import dev.borriguel.devfit.model.dtos.GymEquipmentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface GymEquipmentMapper {
    GymEquipmentResponseDto toGymEquipmentResponseDto(GymEquipment gymEquipment);
    @ObjectFactory
    default GymEquipment toGymEquipment(GymEquipmentRequestDto dto) {
        if (dto == null) return null;
        return new GymEquipment(dto.name(), dto.description(), dto.imageUrl(), dto.category());
    }
}
