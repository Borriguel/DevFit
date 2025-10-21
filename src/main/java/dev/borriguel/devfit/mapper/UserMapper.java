package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.Role;
import dev.borriguel.devfit.model.User;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import dev.borriguel.devfit.model.dtos.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);
    List<UserResponseDto> toUserResponseDtoPage(List<User> users);
    @ObjectFactory
    default User toUser(UserUpdateRequestDto dto) {
        if (dto == null) return null;
        return new User(dto.email(), dto.password(), Role.MEMBER);
    }
}
