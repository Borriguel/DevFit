package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.GymUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUnitRepository extends JpaRepository<GymUnit, Long> {
}