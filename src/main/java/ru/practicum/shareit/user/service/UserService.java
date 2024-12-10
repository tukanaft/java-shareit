package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    List<UserDto> getUsers();

    UserDto getUser(Integer userId);

    void deleteUser(Integer userId);
}
