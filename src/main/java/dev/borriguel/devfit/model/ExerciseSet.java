package dev.borriguel.devfit.model;

import dev.borriguel.devfit.exception.BusinessRuleException;
import dev.borriguel.devfit.exception.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "exercise_sets")
@Getter
@NoArgsConstructor
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private GymEquipment equipment;
    private int reps;
    private int sets;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private TrainingSession session;

    public ExerciseSet(GymEquipment equipment, int reps, int sets) {
        assignEquipment(equipment);
        updateReps(reps);
        updateSets(sets);
    }

    public void assignSession(TrainingSession session) {
        if (this.session != null) throw new BusinessRuleException("Exercise set already assigned to a training session");
        if (session == null) throw new ValidationException("Session cannot be null");
        this.session = session;
        session.addExercise(this);
    }

    public void assignEquipment(GymEquipment equipment) {
        if (equipment == null) throw new ValidationException("Equipment cannot be null");
        this.equipment = equipment;
    }

    public void updateReps(int reps) {
        if (reps < 0) throw new ValidationException("Reps cannot be negative");
        this.reps = reps;
    }

    public void updateSets(int sets) {
        if (sets < 0) throw new ValidationException("Sets cannot be negative");
        this.sets = sets;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseSet that = (ExerciseSet) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
