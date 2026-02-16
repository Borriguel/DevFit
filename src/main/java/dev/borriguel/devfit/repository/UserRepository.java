package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.User;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT new dev.borriguel.devfit.model.dtos.UserResponseDto(u.id, u.email, u.role, new dev.borriguel.devfit.model.dtos.ProfileResponseDto(p.id, p.name, p.document)) FROM User u LEFT JOIN u.profile p")
    Page<UserResponseDto> findAllAsDto(Pageable pageable);
}
