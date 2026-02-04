package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.User;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);
    List<UserResponseDto> toUserResponseDtoPage(List<User> users);
}
