package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.model.Goal;
import dev.borriguel.devfit.model.Member;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import dev.borriguel.devfit.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository repository;

    public Member getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Member not found with id: " + id));
    }

    public Member getByIdWithUnit(Long id) {
        return repository.findByIdWithUnit(id).orElseThrow(() -> new ResourceNotFound("Member not found with id: " + id));
    }

    public Page<MemberResponseDto> getAllAsDto(Pageable page) {
        return repository.findAllAsDto(page);
    }

    public Page<MemberResponseDto> getAllByUnitIdAsDto(Long unitId, Pageable page) {
        return repository.findAllByUnit_Id(unitId, page);
    }

    @Transactional
    public Member updateMetrics(Long id, BigDecimal weight, BigDecimal height) {
        var member = getById(id);
        member.updateMetrics(weight, height);
        return repository.save(member);
    }

    @Transactional
    public void updateGoal(Long id, Goal goal) {
        var member = getById(id);
        member.updateGoal(goal);
        repository.save(member);
    }
}
