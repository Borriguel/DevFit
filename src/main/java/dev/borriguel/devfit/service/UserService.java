package dev.borriguel.devfit.service;

import dev.borriguel.devfit.mapper.UserMapper;
import dev.borriguel.devfit.model.User;
import dev.borriguel.devfit.model.dtos.UserResponseDto;
import dev.borriguel.devfit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Page<User> getAll(Pageable page) {
        return userRepository.findAll(page);
    }

    public Page<UserResponseDto> getAllAsDto(Pageable page) {
        var usersPage = getAll(page);
        var dtoList = mapper.toUserResponseDtoPage(usersPage.getContent());
        return new PageImpl<>(dtoList, page, usersPage.getTotalElements());
    }

    @Transactional
    public void deleteById(Long id) {
        var user = getById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User updateById(Long id, User updated) {
        var userToUpdate = getById(id);
        userToUpdate.updateEmail(updated.getEmail());
        userToUpdate.updatePassword(updated.getPassword());
        return userRepository.save(userToUpdate);
    }
}
