package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_plans")
@Getter
@NoArgsConstructor
public class TrainingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String title;
    @CreationTimestamp
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    @ManyToOne
    private PersonalTrainer personal;
    @ManyToOne
    private Member member;
    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingSession> sessions = new ArrayList<>();

    public TrainingPlan(String title, Goal goal, PersonalTrainer personal, Member member) {
        if (personal == null || member == null) throw new IllegalArgumentException("Personal and member are required");
        if (personal.getUnit() != member.getUnit()) throw new IllegalArgumentException("Personal and member must be assigned to the same gym unit");
        if (goal == null) throw new IllegalArgumentException("Goal cannot be null");
        this.title = title;
        this.goal = goal;
        this.personal = personal;
        this.member = member;
        member.assignTrainingPlan(this);
        personal.assignTrainingPlan(this);
    }

    public void assignSession(TrainingSession session) {
        if (session == null) throw new IllegalArgumentException("Session cannot be null");
        if (sessions.contains(session)) throw new IllegalArgumentException("Session already assigned to this plan");
        sessions.add(session);
        session.assignPlan(this);
    }

    public void updateTitle(String title) {
        if (title.isBlank()) throw new IllegalArgumentException("Title cannot be blank");
        if (title.length() < 3) throw new IllegalArgumentException("Title must be at least 3 characters long");
        if (title.length() > 50) throw new IllegalArgumentException("Title cannot be longer than 20 characters");
        this.title = title;
    }

    public void updateGoal(Goal goal) {
        if (goal == null) throw new IllegalArgumentException("Goal cannot be null");
        this.goal = goal;
    }

    public void updateEndDate(LocalDate endDate) {
        if (endDate == null) throw new IllegalArgumentException("End date cannot be null");
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("End date cannot be before start date");
        this.endDate = endDate;
    }
}
