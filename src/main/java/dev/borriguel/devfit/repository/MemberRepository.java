package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Member;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT new dev.borriguel.devfit.model.dtos.MemberResponseDto(" +
            "m.id, m.name, m.goal, m.weight, m.height, m.unit.id, null) " +
            "FROM Member m WHERE m.unit.id = :id")
    Page<MemberResponseDto> findAllByUnit_Id(Long id, Pageable pageable);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.MemberResponseDto(" +
            "m.id, m.name, m.goal, m.weight, m.height, m.unit.id, null) " +
            "FROM Member m")
    Page<MemberResponseDto> findAllAsDto(Pageable pageable);
}