package dev.borriguel.devfit.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.borriguel.devfit.model.Goal;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TrainingPlanResponseDto(Long id, String title, Goal goal, LocalDate startDate, LocalDate endDate, Long personalId, PersonalTrainerResponseDto personal, Long memberId, MemberResponseDto member) {
}
