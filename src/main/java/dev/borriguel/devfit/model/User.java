package dev.borriguel.devfit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    public User(String email, String password, Role role, Profile profile) {
        updateEmail(email);
        updatePassword(password);
        assignRole(role);
        this.profile = profile;
    }

    public void updatePassword(String password) {
        if (password == null) throw new IllegalArgumentException("Password cannot be null");
        if (password.isBlank()) throw new IllegalArgumentException("Password cannot be blank");
        if (password.length() < 8) throw new IllegalArgumentException("Password must be at least 8 characters long");
        this.password = password;
    }

    public void updateEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email cannot be null");
        if (email.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        if (email.length() < 5) throw new IllegalArgumentException("Email must be at least 5 characters long");
        if (email.length() > 255) throw new IllegalArgumentException("Email cannot be longer than 255 characters");
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) throw new IllegalArgumentException("Invalid email format");
        this.email = email;
    }

    public void assignProfile(Profile profile) {
        if (profile == null) throw new IllegalArgumentException("Profile cannot be null");
        this.profile = profile;
        profile.assignUser(this);
    }

    public void assignRole(Role role) {
        if (role == null) throw new IllegalArgumentException("Role cannot be null");
        this.role = role;
    }
}
