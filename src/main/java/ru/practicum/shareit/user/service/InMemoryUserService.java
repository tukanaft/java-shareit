package ru.practicum.shareit.user.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.HashMap;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class InMemoryUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(UserDto user) {
        log.info("UserService выполнение запроса на добавление пользователя");
        validateUser(user);
        return userMapper.toUserDto(userRepository.addUser(userMapper.toUser(user)));
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userId) {
        log.info("UserService выполнение запроса на обновление пользователя: {}", userId);
        if (!userRepository.isUserExists(userId)) {
            throw new NotFoundException("позователя" + userId + " нет в базе");
        }
        if (user.getEmail() != null) {
            emailvalidation(user, userId);
        }
        return userMapper.toUserDto(userRepository.updateUser(userMapper.toUser(user), userId));
    }

    @Override
    public HashMap<Integer, UserDto> getUsers() {
        log.info("UserService выполнение запроса на отправление всех пользователей");
        return userMapper.toUserDtoHashMap(userRepository.getUsers());
    }

    @Override
    public UserDto getUser(Integer userId) {
        log.info("UserService выполнение запроса на отправление пользователя: {}", userId);
        return userMapper.toUserDto(userRepository.getUser(userId));
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        log.info("UserService выполнение запроса на удвление пользователя: {}", userId);
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

    private void emailvalidation(UserDto user, Integer userId) {
        for (User userToCompare : userRepository.getUsers().values()) {
            if (userToCompare.getEmail().equals(user.getEmail())) {
                throw new ValidationException("пользователь с таким имейлом уже существует");
            }
        }
    }
}
