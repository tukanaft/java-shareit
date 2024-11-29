package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;

public interface UserService {
    Boolean addUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    HashMap<Integer, User> getUsers();

    UserDto getUserDto(Integer userId);

    Boolean deleteUser(Integer userId);
}
