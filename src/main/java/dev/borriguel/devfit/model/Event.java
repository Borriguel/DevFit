package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    private String description;
    @Column(nullable = false, length = 80)
    private String location;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private GymUnit unit;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private List<Profile> attendees = new ArrayList<>();

    public Event(String title, String description, String location, LocalDateTime date) {
        updateTitle(title);
        updateDescription(description);
        updateLocation(location);
        updateDate(date);
    }

    public void assignUnit(GymUnit unit) {
        if (this.unit != null) throw new IllegalStateException("Event already assigned to a gym unit");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        this.unit = unit;
    }

    public void assignAttendee(Profile profile) {
        if (profile == null) throw new IllegalArgumentException("Attendee cannot be null");
        if (attendees.contains(profile)) throw new IllegalArgumentException("Attendee already assigned to this event");
        attendees.add(profile);
    }

    public void removeAttendee(Profile profile) {
        if (profile == null) throw new IllegalArgumentException("Attendee cannot be null");
        attendees.remove(profile);
    }

    public void updateTitle(String title) {
        if (title.isBlank()) throw new IllegalArgumentException("Title cannot be blank");
        if (title.length() < 3) throw new IllegalArgumentException("Title must be at least 3 characters long");
        if (title.length() > 50) throw new IllegalArgumentException("Title cannot be longer than 50 characters");
        this.title = title;
    }

    public void updateDescription(String description) {
        if (description.isBlank()) throw new IllegalArgumentException("Description cannot be blank");
        if (description.length() > 255) throw new IllegalArgumentException("Description cannot be longer than 255 characters");
        this.description = description;
    }

    public void updateLocation(String location) {
        if (location.isBlank()) throw new IllegalArgumentException("Location cannot be blank");
        if (location.length() > 80) throw new IllegalArgumentException("Location cannot be longer than 80 characters");
        this.location = location;
    }

    public void updateDate(LocalDateTime date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
        if (date.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Date cannot be in the past");
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
