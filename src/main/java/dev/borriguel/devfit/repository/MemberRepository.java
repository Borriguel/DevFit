package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"unit"})
    @Query("SELECT m FROM Member m WHERE m.id = :id")
    Optional<Member> findByIdWithUnit(Long id);
}