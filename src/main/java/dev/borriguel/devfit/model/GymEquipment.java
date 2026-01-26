package dev.borriguel.devfit.model;

import dev.borriguel.devfit.exception.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gym_equipments")
@Getter
@NoArgsConstructor
public class GymEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Category category;

    public GymEquipment(String name, String description, String imageUrl, Category category) {
        updateName(name);
        updateDescription(description);
        updateImage(imageUrl);
        updateCategory(category);
    }

    public void updateName(String name) {
        if (name == null) throw new ValidationException("Name cannot be null");
        if (name.isBlank()) throw new ValidationException("Name cannot be blank");
        if (name.length() < 3) throw new ValidationException("Name must be at least 3 characters long");
        if (name.length() > 20) throw new ValidationException("Name cannot be longer than 20 characters");
        this.name = name;
    }

    public void updateDescription(String description) {
        if (description == null) throw new ValidationException("Description cannot be null");
        if (description.isBlank()) throw new ValidationException("Description cannot be blank");
        if (description.length() > 255) throw new ValidationException("Description cannot be longer than 255 characters");
        this.description = description;
    }

    public void updateImage(String imageUrl) {
        if (imageUrl == null) throw new ValidationException("Image cannot be null");
        if (imageUrl.isBlank()) throw new ValidationException("Image cannot be blank");
        if (imageUrl.length() > 255) throw new ValidationException("Image cannot be longer than 255 characters");
        this.imageUrl = imageUrl;
    }

    public void updateCategory(Category category) {
        if (category == null) throw new ValidationException("Category cannot be null");
        if (this.category == category) throw new ValidationException("Category already assigned to this equipment");
        this.category = category;
    }
}
