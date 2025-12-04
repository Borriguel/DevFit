package dev.borriguel.devfit.service;

import dev.borriguel.devfit.mapper.GymUnitMapper;
import dev.borriguel.devfit.model.GymUnit;
import dev.borriguel.devfit.model.dtos.GymUnitResponseDto;
import dev.borriguel.devfit.repository.GymUnitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymUnitService {
    private final GymUnitRepository repository;
    private final GymUnitMapper mapper;
    private final MemberService memberService;
    private final PersonalTrainerService personalService;

    @Transactional
    public GymUnit createGymUnit(GymUnit gym) {
        return repository.save(gym);
    }

    public GymUnit getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Gym unit not found"));
    }

    public Page<GymUnit> getAll(Pageable page) {
        return repository.findAll(page);
    }

    @Transactional
    public GymUnit updateMonthlyFeeById(Long id, BigDecimal newMonthlyFee) {
        var gymUnit = getById(id);
        gymUnit.updateMonthlyFee(newMonthlyFee);
        return repository.save(gymUnit);
    }

    public Page<GymUnitResponseDto> getAllAsDto(Pageable page) {
        var gymUnits = getAll(page);
        var dtoList = mapper.toGymUnitResponseDtoPage(gymUnits.getContent());
        return new PageImpl<>(dtoList, page, gymUnits.getTotalElements());
    }

    public void deleteById(Long id) {
        var gymUnit = getById(id);
        repository.delete(gymUnit);
    }

    @Transactional
    public GymUnit updateById(Long id, GymUnit updated) {
        var gymUnitToUpdate = getById(id);
        gymUnitToUpdate.updateName(updated.getName());
        gymUnitToUpdate.updateAddress(updated.getAddress());
        gymUnitToUpdate.updateMonthlyFee(updated.getMonthlyFee());
        return repository.save(gymUnitToUpdate);
    }

    @Transactional
    public void transferMember(Long memberId, Long destinationUnitId) {
        var member = memberService.getById(memberId);
        var gymUnit = getById(member.getUnit().getId());
        var destinationUnit = getById(destinationUnitId);
        gymUnit.transferMember(member, destinationUnit);
    }

    @Transactional
    public void transferPersonal(Long trainerId, Long destinationUnitId) {
        var trainer = personalService.getById(trainerId);
        var gymUnit = getById(trainer.getUnit().getId());
        var destinationUnit = getById(destinationUnitId);
        gymUnit.transferPersonal(trainer, destinationUnit);
    }
}
