package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
public class Member extends Profile {
    @Enumerated(EnumType.STRING)
    private Goal goal;
    private BigDecimal weight;
    private BigDecimal height;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private GymUnit unit;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<TrainingPlan> plans = new ArrayList<>();

    public Member(String name, String document) {
        super(name, document);
    }

    public void assignUnit(GymUnit unit) {
        if (this.unit != null) throw new IllegalStateException("Member already assigned to a gym unit");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (unit.getMembers().contains(this)) throw new IllegalArgumentException("Unit already contains this member");
        this.unit = unit;
    }

    public void updateGoal(Goal goal) {
        if (goal == null) throw new IllegalArgumentException("Goal cannot be null");
        this.goal = goal;
    }

   public void updateMetrics(BigDecimal weight, BigDecimal height) {
        if (weight.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Weight cannot be negative");
        if (height.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Height cannot be negative");
        if (weight.scale() > 2) throw new IllegalArgumentException("Weight cannot have more than 2 decimal places");
        if (height.scale() > 2) throw new IllegalArgumentException("Height cannot have more than 2 decimal places");
        this.weight = weight;
        this.height = height;
    }

    public void assignTrainingPlan(TrainingPlan plan) {
        if (!plans.contains(plan)) {
            plans.add(plan);
        }
    }
}
