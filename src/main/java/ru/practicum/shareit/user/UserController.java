package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto addUser(@RequestBody UserDto user) {
        log.info("UserController выполнение запроса на добавление пользователя");
        return userService.addUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto user, @PathVariable("userId") Integer userId) {
        log.info("UserController выполнение запроса на обновление пользователя: {}", userId);
        return userService.updateUser(user, userId);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("UserController выполнение запроса на отправление всех пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Integer userId) {
        log.info("UserController выполнение запроса на отправление пользователя: {}", userId);
        return userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("UserController выполнение запроса на удаление пользователя: {}", userId);
        userService.deleteUser(userId);
    }
}
