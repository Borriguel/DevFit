package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Event;
import dev.borriguel.devfit.model.dtos.EventResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = {"attendees"})
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Optional<Event> findByIdWithAttendees(Long id);

    @EntityGraph(attributePaths = {"unit"})
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Optional<Event> findByIdWithGymUnit(Long id);

    @EntityGraph(attributePaths = {"attendees", "unit"})
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Optional<Event> findByIdWithAttendeesAndGymUnit(Long id);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.EventResponseDto(" +
            "e.id, e.title, e.description, e.location, e.date, e.unit.id, null, SIZE(e.attendees), null) " +
            "FROM Event e LEFT JOIN e.unit u WHERE e.id = :id")
    Page<EventResponseDto> findByUnitIdAsDto(Long id, Pageable pageable);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.EventResponseDto(" +
            "e.id, e.title, e.description, e.location, e.date, e.unit.id, null, SIZE(e.attendees), null) " +
            "FROM Event e LEFT JOIN e.unit u")
    Page<EventResponseDto> findAllAsDto(Pageable pageable);
}