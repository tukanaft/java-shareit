package ru.practicum.shareit.user.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.Repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class InMemoryUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(UserDto user) {
        validateUser(user);
        return userRepository.addUser(user);
    }

    @Override
    public User updateUser(UserDto user, Integer userId) {
        if (!userRepository.isUserExists(userId)) {
            throw new NotFoundException("позователя нет в базе");
        }
        if (user.getEmail() != null) {
            emailvalidation(user);
        }
        return userRepository.updateUser(user, userId);
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User getUser(Integer userId) {
        return userRepository.getUser(userId);
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        return userRepository.deleteUser(userId);
    }

    private void validateUser(UserDto user) {
        if (user.getName() == null) {
            throw new ValidationException("не введено имя пользователя");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidationException("не коректный email");
        }
        for (User userCopy : userRepository.getUsers().values()) {
            if (userCopy.getEmail().equals(user.getEmail())) {
                throw new ValidationException("пользователем с таким имеилом уже добавлен в базу");
            }
        }
    }

    private void emailvalidation(UserDto user) {
        for (User userToCompare : userRepository.getUsers().values()) {
            if (userToCompare.getEmail().equals(user.getEmail())) {
                throw new ValidationException("пользователь с таким имейлом уже существует");
            }
        }
    }
}
