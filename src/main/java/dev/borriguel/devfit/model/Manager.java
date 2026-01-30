package dev.borriguel.devfit.model;

import dev.borriguel.devfit.exception.BusinessRuleException;
import dev.borriguel.devfit.exception.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "managers")
@Getter
@NoArgsConstructor
public class Manager extends Profile {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", unique = true)
    private GymUnit unit;

    public Manager(String name, String document) {
        super(name, document);
    }

    public void assignUnit(GymUnit unit) {
        if (this.unit != null) throw new BusinessRuleException("Manager already assigned to a gym unit");
        if (unit == null) throw new ValidationException("Unit cannot be null");
        this.unit = unit;
    }

    public void unassignUnit() {
        this.unit = null;
    }
}
