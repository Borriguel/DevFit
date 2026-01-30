package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Manager;
import dev.borriguel.devfit.model.dtos.ManagerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @EntityGraph(attributePaths = {"unit"})
    @Query("SELECT m FROM Manager m WHERE m.id = :id")
    Optional<Manager> findByIdWithUnit(Long id);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.ManagerResponseDto(" +
            "m.id, m.name, u.id, null) " +
            "FROM Manager m LEFT JOIN m.unit u")
    Page<ManagerResponseDto> findAllAsDto(Pageable pageable);
}