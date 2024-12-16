package ru.practicum.shareit.user.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public ru.practicum.shareit.user.dto.UserDto toUserDto(User user) {
        return new ru.practicum.shareit.user.dto.UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User toUser(ru.practicum.shareit.user.dto.UserDto user) {
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public List<ru.practicum.shareit.user.dto.UserDto> toUserDtoList(List<User> users) {
        List<ru.practicum.shareit.user.dto.UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            ));
        }
        return usersDto;
    }
}
