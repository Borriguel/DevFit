package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
public class Member extends Profile {
    @Enumerated(EnumType.STRING)
    private Goal goal = Goal.HEALTH;
    private BigDecimal weight = BigDecimal.ZERO;
    private BigDecimal height = BigDecimal.ZERO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private GymUnit unit;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<TrainingPlan> plans = new ArrayList<>();

    public Member(String name, String document) {
        super(name, document);
    }

    void assignUnit(GymUnit unit) {
        if (this.unit != null) throw new IllegalStateException("Member already assigned to a gym unit");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        this.unit = unit;
    }

    public void reassignUnit(GymUnit newUnit) {
        if (newUnit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (this.unit == newUnit) return;
        this.unit = newUnit;
    }

    public void updateGoal(Goal goal) {
        if (goal == null) throw new IllegalArgumentException("Goal cannot be null");
        this.goal = goal;
    }

    public void updateMetrics(BigDecimal weight, BigDecimal height) {
        if (weight.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Weight cannot be negative");
        if (height.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Height cannot be negative");
        if (weight.stripTrailingZeros().scale() > 2) throw new IllegalArgumentException("Weight cannot have more than 2 decimal places");
        if (height.stripTrailingZeros().scale() > 2) throw new IllegalArgumentException("Height cannot have more than 2 decimal places");
        this.weight = weight;
        this.height = height;
    }

    public void assignTrainingPlan(TrainingPlan plan) {
        if (!plans.contains(plan)) {
            plans.add(plan);
        }
    }

    public TrainingPlan requestTrainingPlan(PersonalTrainer personalTrainer, String title) {
        if (personalTrainer == null) throw new IllegalArgumentException("Personal trainer cannot be null");
        if (!this.getUnit().equals(personalTrainer.getUnit())) throw new IllegalArgumentException("Personal trainer must be assigned to the same gym unit");
        if (this.getGoal() == null) throw new IllegalStateException("Member must have a goal");
        return new TrainingPlan(title, this.getGoal(), personalTrainer, this);
    }
}
