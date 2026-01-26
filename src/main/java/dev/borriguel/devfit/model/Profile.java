package dev.borriguel.devfit.model;

import dev.borriguel.devfit.exception.BusinessRuleException;
import dev.borriguel.devfit.exception.ValidationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 11)
    private String document;

    protected Profile(String name, String document) {
        updateName(name);
        updateDocument(document);
    }

    public void assignUser(User user) {
        if (user == null) throw new ValidationException("User cannot be null");
        if (this.user != null) throw new BusinessRuleException("Profile already assigned to another user");
        this.user = user;
    }

    public void updateName(String name) {
        if (name == null) throw new ValidationException("Name cannot be null");
        if (name.isBlank()) throw new ValidationException("Name cannot be blank");
        if (name.length() < 3) throw new ValidationException("Name must be at least 3 characters long");
        if (name.length() > 50) throw new ValidationException("Name cannot be longer than 50 characters");
        this.name = name;
    }

    public void updateDocument(String document) {
        if (document == null) throw new ValidationException("Document cannot be null");
        if (document.isBlank()) throw new ValidationException("Document cannot be blank");
        if (document.length() < 11) throw new ValidationException("Document must be at least 11 characters long");
        if (document.length() > 11) throw new ValidationException("Document cannot be longer than 11 characters");
        if (!document.matches("^[0-9]{11}$")) throw new ValidationException("Invalid document format");
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
