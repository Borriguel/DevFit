package dev.borriguel.devfit.service;

import dev.borriguel.devfit.exception.ResourceNotFound;
import dev.borriguel.devfit.mapper.PersonalTrainerMapper;
import dev.borriguel.devfit.model.PersonalTrainer;
import dev.borriguel.devfit.model.dtos.PersonalTrainerResponseDto;
import dev.borriguel.devfit.repository.PersonalTrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalTrainerService {
    private final PersonalTrainerRepository repository;
    private final PersonalTrainerMapper mapper;

    public PersonalTrainer getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("Personal trainer not found with id: " + id));
    }

    public PersonalTrainer getByIdWithUnit(Long id) {
        return repository.findByIdWithUnit(id).orElseThrow(() -> new ResourceNotFound("Personal trainer not found with id: " + id));
    }

    public Page<PersonalTrainer> getAllByUnitId(Long unitId, Pageable page) {
        return repository.findByUnit_Id(unitId, page);
    }

    public Page<PersonalTrainerResponseDto> getAllByUnitIdAsDto(Long unitId, Pageable page) {
        var personalTrainersPage = getAllByUnitId(unitId, page);
        var dtoList = mapper.toSimpleDtoList(personalTrainersPage.getContent());
        return new PageImpl<>(dtoList, page, personalTrainersPage.getTotalElements());
    }
}
