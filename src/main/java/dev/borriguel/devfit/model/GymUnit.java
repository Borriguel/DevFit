package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gym_units")
@Getter
@NoArgsConstructor
public class GymUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    private String address;
    private BigDecimal monthlyFee;
    @OneToOne(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Manager manager;
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalTrainer> personalTrainers = new ArrayList<>();
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    public GymUnit(String name, String address, BigDecimal monthlyFee) {
        updateName(name);
        updateAddress(address);
        updateMonthlyFee(monthlyFee);
    }

    public void assignManager(Manager manager) {
        if (this.manager != null) throw new IllegalStateException("Gym unit already has a manager");
        if (manager == null) throw new IllegalArgumentException("Manager cannot be null");
        if (manager.getUnit() != null) throw new IllegalArgumentException("Manager already assigned to another gym unit");
        this.manager = manager;
        manager.assignUnit(this);
    }

    public void assignMember(Member member) {
        if (member == null) throw new IllegalArgumentException("Member cannot be null");
        if (members.contains(member)) throw new IllegalArgumentException("Member already assigned to this gym unit");
        members.add(member);
        member.assignUnit(this);
    }

    public void assignPersonalTrainer(PersonalTrainer personalTrainer) {
        if (personalTrainer == null) throw new IllegalArgumentException("Personal cannot be null");
        if (personalTrainers.contains(personalTrainer)) throw new IllegalArgumentException("Personal trainer already assigned to this gym unit");
        personalTrainers.add(personalTrainer);
        personalTrainer.assignUnit(this);
    }

    public void updateName(String name) {
        if (name == null) throw new IllegalArgumentException("Name cannot be null");
        if (name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        if (name.length() < 3) throw new IllegalArgumentException("Name must be at least 3 characters long");
        if (name.length() > 50) throw new IllegalArgumentException("Name cannot be longer than 50 characters");
        this.name = name;
    }

    public void updateAddress(String address) {
        if (address == null) throw new IllegalArgumentException("Address cannot be null");
        if (address.isBlank()) throw new IllegalArgumentException("Address cannot be blank");
        if (address.length() < 5) throw new IllegalArgumentException("Address must be at least 5 characters long");
        if (address.length() > 255) throw new IllegalArgumentException("Address cannot be longer than 255 characters");
        this.address = address;
    }

    public void updateMonthlyFee(BigDecimal monthlyFee) {
        if (monthlyFee == null) throw new IllegalArgumentException("Monthly fee cannot be null");
        if (monthlyFee.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Monthly fee cannot be negative");
        if (monthlyFee.compareTo(BigDecimal.ZERO) == 0) throw new IllegalArgumentException("Monthly fee cannot be zero");
        if (monthlyFee.stripTrailingZeros().scale() > 2) throw new IllegalArgumentException("Monthly fee cannot have more than 2 decimal places");
        this.monthlyFee = monthlyFee;
    }

    public void transferMember(Member member, GymUnit destination) {
        if (member == null) throw new IllegalArgumentException("Member cannot be null");
        if (destination == null) throw new IllegalArgumentException("Destination gym unit cannot be null");
        if (destination.equals(this)) throw new IllegalArgumentException("Destination gym unit cannot be the same as the source gym unit");
        members.remove(member);
        member.reassignUnit(destination);
        destination.members.add(member);
    }

    public void transferPersonal(PersonalTrainer trainer, GymUnit destination) {
        if (trainer == null) throw new IllegalArgumentException("Personal trainer cannot be null");
        if (destination == null) throw new IllegalArgumentException("Destination gym unit cannot be null");
        if (destination.equals(this)) throw new IllegalArgumentException("Destination gym unit cannot be the same as the source gym unit");
        personalTrainers.remove(trainer);
        trainer.reassignUnit(destination);
        destination.personalTrainers.add(trainer);
    }
}
