package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Member;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Named("simpleMapping")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unit", ignore = true)
    MemberResponseDto toSimpleDto(Member member);

    @Mapping(target = "unitId", source = "unit.id")
    MemberResponseDto toExpandedDto(Member member);

    @IterableMapping(qualifiedByName = "simpleMapping")
    List<MemberResponseDto> toSimpleDtoList(List<Member> members);
}
