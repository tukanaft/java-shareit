package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;


@Component
public class InMemoryUserRepository implements UserRepository {
    private HashMap<Integer, User> users;
    private Integer userId;

    public InMemoryUserRepository(UserMapper userMapper) {
        users = new HashMap<>();
        userId = 1;
    }

    @Override
    public User addUser(User user) {
        user.setId(userId);
        users.put(user.getId(), user);
        userId++;
        return user;
    }

    @Override
    public User updateUser(User user, Integer userId) {
        if (user.getName() != null) {
            users.get(userId).setName(user.getName());
        }
        if (user.getEmail() != null) {
            users.get(userId).setEmail(user.getEmail());
        }
        return users.get(userId);
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
    public Boolean isUserExists(Integer userId) {
        return users.containsKey(userId);
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        users.remove(userId);
        return true;
    }
}
