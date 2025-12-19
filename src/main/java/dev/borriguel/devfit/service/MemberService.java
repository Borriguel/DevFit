package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.mapper.MemberMapper;
import dev.borriguel.devfit.model.Goal;
import dev.borriguel.devfit.model.Member;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import dev.borriguel.devfit.repository.MemberRepository;
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
public class MemberService {
    private final MemberRepository repository;
    private final MemberMapper mapper;

    public Member getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Member not found with id: " + id));
    }

    public Member getByIdWithUnit(Long id) {
        return repository.findByIdWithUnit(id).orElseThrow(() -> new ResourceNotFound("Member not found with id: " + id));
    }

    public Page<Member> getAll(Pageable page) {
        return repository.findAll(page);
    }

    public Page<MemberResponseDto> getAllAsDto(Pageable page) {
        var membersPage = getAll(page);
        var dtoList = mapper.toSimpleDtoList(membersPage.getContent());
        return new PageImpl<>(dtoList, page, membersPage.getTotalElements());
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
