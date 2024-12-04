package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.HashMap;

public interface UserRepository {
    User addUser(User user);

    User updateUser(User user, Integer userId);

    HashMap<Integer, User> getUsers();

    User getUser(Integer userId);

    Boolean isUserExists(Integer userId);

    Boolean deleteUser(Integer userId);
}
