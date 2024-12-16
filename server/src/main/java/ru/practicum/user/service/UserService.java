package ru.practicum.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.user.dto.UserDto;

import java.util.List;

@Service
public interface UserService {
    UserDto addUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    List<UserDto> getUsers();

    UserDto getUser(Integer userId);

    boolean deleteUser(Integer userId);
}
