package dev.borriguel.devfit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@NoArgsConstructor
public class Admin extends Profile {
    public Admin(String name, String document) {
        super(name, document);
    }
}
