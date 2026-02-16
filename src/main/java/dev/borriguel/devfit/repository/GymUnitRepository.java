package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.GymUnit;
import dev.borriguel.devfit.model.dtos.GymUnitResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GymUnitRepository extends JpaRepository<GymUnit, Long> {
    @Query("SELECT new dev.borriguel.devfit.model.dtos.GymUnitResponseDto(u.id, u.name, u.address, u.monthlyFee) FROM GymUnit u")
    Page<GymUnitResponseDto> findAllAsDto(Pageable pageable);
}