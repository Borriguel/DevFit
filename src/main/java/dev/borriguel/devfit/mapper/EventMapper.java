package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Event;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Named("simpleMapping")
    @Mapping(target = "totalAttendees", source = "numberOfAttendees")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "attendees", ignore = true)
    EventResponseDto toSimpleDto(Event event);

    @Mapping(target = "totalAttendees", source = "numberOfAttendees")
    @Mapping(target = "unitId", source = "unit.id")
    EventResponseDto toExpandedDto(Event event);

    @Mapping(target = "totalAttendees", source = "numberOfAttendees")
    @Mapping(target = "attendees", ignore = true)
    @Mapping(target = "unitId", source = "unit.id")
    EventResponseDto toExpandDtoWithGymUnitOnly(Event event);

    @Mapping(target = "totalAttendees", source = "numberOfAttendees")
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "unitId", source = "unit.id")
    EventResponseDto toExpandedDtoWithAttendeesOnly(Event event);

    @ObjectFactory
    default Event toEvent(EventRequestDto dto) {
        if (dto == null) return null;
        return new Event(dto.title(), dto.description(), dto.location(), dto.date());
    }
}
