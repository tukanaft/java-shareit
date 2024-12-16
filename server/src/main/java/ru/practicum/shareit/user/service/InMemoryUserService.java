package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

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
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(user)));
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userId) {
        log.info("UserService выполнение запроса на обновление пользователя: {}", userId);
        User userToUpdate = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("позователя" + userId + " нет в базе"));
        if (user.getEmail() != null) {
            emailvalidation(user, userId);
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            userToUpdate.setName(user.getName());
        }
        return userMapper.toUserDto(userRepository.save(userToUpdate));
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("UserService выполнение запроса на отправление всех пользователей");
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Integer userId) {
        log.info("UserService выполнение запроса на отправление пользователя: {}", userId);
        return userMapper.toUserDto(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("позователя" + userId + " нет в базе")));
    }

    @Override
    public boolean deleteUser(Integer userId) {
        log.info("UserService выполнение запроса на удвление пользователя: {}", userId);
        userRepository.deleteById(userId);
        return true;
    }

    private void emailvalidation(UserDto user, Integer userId) {
        for (User userToCompare : userRepository.findAll()) {
            if (userToCompare.getEmail().equals(user.getEmail())) {
                throw new ValidationException("пользователь с таким имейлом уже существует");
            }
        }
    }
}
