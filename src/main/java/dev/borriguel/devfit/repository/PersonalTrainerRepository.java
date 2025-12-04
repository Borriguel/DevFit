package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.PersonalTrainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {
    @EntityGraph(attributePaths = {"unit"})
    @Query("SELECT p FROM PersonalTrainer p WHERE p.id = :id")
    Optional<PersonalTrainer> findByIdWithUnit(Long id);

    @Query("select p from PersonalTrainer p where p.unit.id = ?1")
    Page<PersonalTrainer> findByUnit_Id(Long id, Pageable pageable);


}