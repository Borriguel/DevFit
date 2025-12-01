package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlan trainingPlan;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSet> exercises = new ArrayList<>();
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutLog> logs = new ArrayList<>();

    public void assignPlan(TrainingPlan trainingPlan, PersonalTrainer personal) {
        if (this.trainingPlan != null) throw new IllegalStateException("Training session already assigned to a training plan");
        if (trainingPlan == null) throw new IllegalArgumentException("Training plan cannot be null");
        if (trainingPlan.getPersonal() != personal) throw new IllegalArgumentException("Training plan not assigned to personal trainer");
        this.trainingPlan = trainingPlan;
        trainingPlan.assignSession(this);
    }

    public void addExercise(ExerciseSet exercise) {
        if (exercise == null) throw new IllegalArgumentException("Exercise cannot be null");
        if (exercises.contains(exercise)) throw new IllegalArgumentException("Exercise already assigned to this session");
        exercises.add(exercise);
    }

    public void addToLog(WorkoutLog log) {
        if (log == null) throw new IllegalArgumentException("Log cannot be null");
        if (log.getPerformedOn() == null) throw new IllegalArgumentException("Log performed on date cannot be null");
        logs.add(log);
    }

    public int getNumberOfExercises() {
        return exercises.size();
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
