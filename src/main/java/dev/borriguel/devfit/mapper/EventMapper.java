package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Event;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventResponseDto toEventResponseDto(Event event);
    List<EventResponseDto> toEventResponseDtoPage(List<Event> events);

    @ObjectFactory
    default Event toEvent(EventRequestDto dto) {
        if (dto == null) return null;
        return new Event(dto.title(), dto.description(), dto.location(), dto.date());
    }
}
