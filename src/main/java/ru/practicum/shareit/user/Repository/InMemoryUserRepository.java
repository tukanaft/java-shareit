package ru.practicum.shareit.user.Repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;


@Component
public class InMemoryUserRepository implements UserRepository {
    private HashMap<Integer, User> users;
    private Integer userId;
    private UserMapper userMapper;

    public InMemoryUserRepository(UserMapper userMapper) {
        users = new HashMap<>();
        userId = 1;
        this.userMapper = userMapper;
    }

    @Override
    public Boolean addUser(UserDto user) {
        User userToSave = userMapper.toUser(user, userId);
        users.put(userToSave.getId(), userToSave);
        return true;
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userId) {
        if (user.getName() != null) {
            users.get(userId).setName(user.getName());
        }
        if (user.getEmail() != null) {
            users.get(userId).setEmail(user.getEmail());
        }
        return userMapper.toUserDto(users.get(userId));
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User getUser(Integer userId) {
        return (users.get(userId));
    }

    @Override
    public UserDto getUserDto(Integer userId) {
        return userMapper.toUserDto(users.get(userId));
    }

    @Override
    public Boolean isUserExists(Integer userId) {
        return users.containsKey(userId);
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        users.remove(userId);
        return true;
    }
}
