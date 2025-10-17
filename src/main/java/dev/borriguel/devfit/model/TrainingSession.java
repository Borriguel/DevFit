package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "training_sessions")
@Getter
@NoArgsConstructor
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlan trainingPlan;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSet> exercises = new ArrayList<>();
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutLog> logs = new ArrayList<>();

    public void assignPlan(TrainingPlan trainingPlan) {
        if (this.trainingPlan != null) throw new IllegalStateException("Training session already assigned to a training plan");
        if (trainingPlan == null) throw new IllegalArgumentException("Training plan cannot be null");
        this.trainingPlan = trainingPlan;
    }

    public void assignExercise(ExerciseSet exercise) {
        if (exercise == null) throw new IllegalArgumentException("Exercise cannot be null");
        if (exercises.contains(exercise)) throw new IllegalArgumentException("Exercise already assigned to this session");
        exercises.add(exercise);
        exercise.assignSession(this);
    }

    public void removeExercise(ExerciseSet exercise) {
        if (exercise == null) throw new IllegalArgumentException("Exercise cannot be null");
        if (!exercises.contains(exercise)) throw new IllegalArgumentException("Exercise not assigned to this session");
        exercises.remove(exercise);
        exercise.assignSession(null);
    }

    public void addToLog(WorkoutLog log) {
        if (log == null) throw new IllegalArgumentException("Log cannot be null");
        if (logs.contains(log)) throw new IllegalArgumentException("Log already assigned to this session");
        if (log.getSession() != null) throw new IllegalArgumentException("Log already assigned to another session");
        if (log.getPerformedOn() == null) throw new IllegalArgumentException("Log performed on date cannot be null");
        logs.add(log);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSession that = (TrainingSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
