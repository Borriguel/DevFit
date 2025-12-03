package dev.borriguel.devfit.service;

import dev.borriguel.devfit.mapper.EventMapper;
import dev.borriguel.devfit.model.Event;
import dev.borriguel.devfit.model.dtos.EventRequestDto;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import dev.borriguel.devfit.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;
    private final EventMapper mapper;
    private final GymUnitService gymUnitService;
    private final ProfileService profileService;

    @Transactional
    public Event createEvent(EventRequestDto dto) {
        var gym = gymUnitService.getById(dto.gymUnitId());
        var event = mapper.toEvent(dto);
        event.assignUnit(gym);
        return repository.save(event);
    }

    public Event getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public Event getByIdWithAttendees(Long id) {
        return repository.findByIdWithAttendees(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public Page<Event> getAll(Pageable page) {
        return repository.findAll(page);
    }

    public Page<Event> getAllByGymId(Long gymId, Pageable page) {
        return repository.findByUnit_Id(gymId, page);
    }

    public Page<EventResponseDto> getAllAsDto(Pageable page) {
        var eventsPage = getAll(page);
        var dtoList = mapper.toEventResponseDtoPage(eventsPage.getContent());
        return new PageImpl<>(dtoList, page, eventsPage.getTotalElements());
    }

    public Page<EventResponseDto> getAllByGymIdAsDto(Long gymId, Pageable page) {
        var eventsPage = getAllByGymId(gymId, page);
        var dtoList = mapper.toEventResponseDtoPage(eventsPage.getContent());
        return new PageImpl<>(dtoList, page, eventsPage.getTotalElements());
    }

    @Transactional
    public void deleteById(Long id) {
        var event = getById(id);
        repository.delete(event);
    }

    @Transactional
    public Event updateById(Long id, Event eventUpdated) {
        var eventToUpdate = getById(id);
        eventToUpdate.updateTitle(eventUpdated.getTitle());
        eventToUpdate.updateDescription(eventUpdated.getDescription());
        eventToUpdate.updateLocation(eventUpdated.getLocation());
        eventToUpdate.updateDate(eventUpdated.getDate());
        return repository.save(eventToUpdate);
    }

    @Transactional
    public void joinEvent(Long id, Long profileId) {
        var event = getById(id);
        var profile = profileService.getById(profileId);
        event.assignAttendee(profile);
        repository.save(event);
    }

    @Transactional
    public void leaveEvent(Long id, Long profileId) {
        var event = getById(id);
        var profile = profileService.getById(profileId);
        event.removeAttendee(profile);
        repository.save(event);
    }
}
