package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Member;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unit", ignore = true)
    MemberResponseDto toSimpleDto(Member member);

    @Mapping(target = "unitId", source = "unit.id")
    MemberResponseDto toExpandedDto(Member member);
}
