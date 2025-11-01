package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Event;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "unit.name", target = "gymUnit")
    @Mapping(source = "attendees", target = "totalAttendees", qualifiedByName = "countAttendees")
    EventResponseDto toEventResponseDto(Event event);
    
    List<EventResponseDto> toEventResponseDtoPage(List<Event> events);

    @Named("countAttendees")
    default int countAttendees(List<?> attendees) {
        return attendees != null ? attendees.size() : 0;
    }

    @ObjectFactory
    default Event toEvent(EventRequestDto dto) {
        if (dto == null) return null;
        return new Event(dto.title(), dto.description(), dto.location(), dto.date());
    }
}
