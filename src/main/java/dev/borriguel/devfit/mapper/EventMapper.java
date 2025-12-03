package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Event;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Named("simpleMapping")
    @Mapping(target = "gymUnit", source = "unit.name")
    @Mapping(target = "totalAttendees", source = "numberOfAttendees")
    @Mapping(target = "attendees", ignore = true)
    EventResponseDto toSimpleDto(Event event);

    @Mapping(target = "gymUnit", source = "unit.name")
    @Mapping(target = "totalAttendees", source = "numberOfAttendees")
    EventResponseDto toExpandedDto(Event event);

    @IterableMapping(qualifiedByName = "simpleMapping")
    List<EventResponseDto> toEventResponseDtoPage(List<Event> events);

    @ObjectFactory
    default Event toEvent(EventRequestDto dto) {
        if (dto == null) return null;
        return new Event(dto.title(), dto.description(), dto.location(), dto.date());
    }
}
