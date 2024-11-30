package ru.practicum.shareit.user.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User toUser(UserDto user) {
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public HashMap<Integer, UserDto> toUserDtoHashMap(HashMap<Integer, User> users) {
        HashMap<Integer, UserDto> usersDto = new HashMap<>();
        for (User user : users.values()) {
            usersDto.put(user.getId(), new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            ));
        }
        return usersDto;
    }
}
