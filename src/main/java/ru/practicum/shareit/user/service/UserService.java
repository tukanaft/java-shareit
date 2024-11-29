package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;

public interface UserService {
    User addUser(UserDto user);

    User updateUser(UserDto user, Integer userId);

    HashMap<Integer, User> getUsers();

    User getUser(Integer userId);

    Boolean deleteUser(Integer userId);
}
