package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.HashMap;

public interface UserService {
    UserDto addUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    HashMap<Integer, UserDto> getUsers();

    UserDto getUser(Integer userId);

    Boolean deleteUser(Integer userId);
}
