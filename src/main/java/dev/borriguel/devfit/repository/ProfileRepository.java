package dev.borriguel.devfit.repository;

import dev.borriguel.devfit.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select (count(p) > 0) from Profile p where p.document = ?1")
    boolean existsByDocument(String document);

}
