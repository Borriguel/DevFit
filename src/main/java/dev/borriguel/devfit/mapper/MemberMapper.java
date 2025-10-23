package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Member;
import dev.borriguel.devfit.model.dtos.MemberResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberResponseDto toMemberResponseDto(Member member);
    List<MemberResponseDto> toMemberResponseDtoPage(List<Member> members);
}
