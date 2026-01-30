package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ValidationException;
import dev.borriguel.devfit.model.*;
import dev.borriguel.devfit.model.dtos.ManagerRegistrationDto;
import dev.borriguel.devfit.model.dtos.MemberRegistrationDto;
import dev.borriguel.devfit.model.dtos.PersonalTrainerRegistrationDto;
import dev.borriguel.devfit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserRepository repository;
    private final GymUnitService gymUnitService;
    private final ProfileService profileService;

    @Transactional
    public User registerManager(ManagerRegistrationDto dto) {
        var user = new User(dto.email(), dto.password(), Role.MANAGER);
        var manager = new Manager(dto.name(), dto.document());
        return registerAndAssignProfile(user, manager, dto.gymId(), GymUnit::assignManager);
    }

    @Transactional
    public User registerPersonalTrainer(PersonalTrainerRegistrationDto dto) {
        var user = new User(dto.email(), dto.password(), Role.PERSONAL_TRAINER);
        var personalTrainer = new PersonalTrainer(dto.name(), dto.document());
        return registerAndAssignProfile(user, personalTrainer, dto.gymId(), GymUnit::assignPersonalTrainer);
    }

    @Transactional
    public User registerMember(MemberRegistrationDto dto) {
        var user = new User(dto.email(), dto.password(), Role.MEMBER);
        var member = new Member(dto.name(), dto.document(), dto.goal(), dto.weight(), dto.height());
        return registerAndAssignProfile(user, member, dto.gymId(), GymUnit::assignMember);
    }

    private <P extends Profile> User registerAndAssignProfile(User user, P profile, Long gymId, BiConsumer<GymUnit, P> gymAssigner) {
        validateRegistration(user, profile);
        var gym = gymUnitService.getById(gymId);
        gymAssigner.accept(gym, profile);
        user.assignProfile(profile);
        return repository.save(user);
    }

    private void validateRegistration(User user, Profile profile) {
        validateUniqueEmail(user.getEmail());
        validateUniqueProfileDocument(profile.getDocument());
    }

    private void validateUniqueEmail(String email) {
        if (repository.existsByEmail(email)) throw new ValidationException("User already registered");
    }

    private void validateUniqueProfileDocument(String document) {
        if (profileService.existsByDocument(document)) throw new ValidationException("Profile already registered");
    }
}
