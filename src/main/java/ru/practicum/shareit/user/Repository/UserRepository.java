package ru.practicum.shareit.user.Repository;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;

public interface UserRepository {
    User addUser(UserDto user);

    User updateUser(UserDto user, Integer userId);

    HashMap<Integer, User> getUsers();

    User getUser(Integer userId);

    UserDto getUserDto(Integer userId);

    Boolean isUserExists(Integer userId);

    Boolean deleteUser(Integer userId);
}
