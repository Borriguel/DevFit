package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal_trainers")
@Getter
@NoArgsConstructor
public class PersonalTrainer extends Profile {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private GymUnit unit;
    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingPlan> plans = new ArrayList<>();

    public PersonalTrainer(String name, String document) {
        super(name, document);
    }

    public void assignUnit(GymUnit unit) {
        if (this.unit != null) throw new IllegalStateException("Personal trainer already assigned to a gym unit");
        if (unit == null) throw new IllegalArgumentException("Gym unit cannot be null");
        this.unit = unit;
    }

    public void reassignUnit(GymUnit newUnit) {
        if (newUnit == null) throw new IllegalArgumentException("Gym unit cannot be null");
        if (this.unit == newUnit) return;
        this.unit = newUnit;
    }

    public void assignTrainingPlan(TrainingPlan plan) {
        if (!plans.contains(plan)) {
            plans.add(plan);
        }
    }

    public TrainingSession addSessionToPlan(TrainingPlan plan) {
        if (plan == null) throw new IllegalArgumentException("Plan is required");
        if (!this.getPlans().contains(plan)) throw new IllegalArgumentException("Plan is not assigned to this personal trainer");
        var session = new TrainingSession();
        plan.assignSession(session);
        return session;
    }

    
}
