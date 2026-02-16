package dev.borriguel.devfit.mapper;

import dev.borriguel.devfit.model.User;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);
}
