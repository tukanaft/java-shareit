package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.HashMap;

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
    public Boolean addUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto user, @PathVariable("userId") Integer userId) {
        return userService.updateUser(user, userId);
    }

    @GetMapping
    public HashMap<Integer,User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Integer userId){
        return userService.getUserDto(userId);
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable Integer userId){
        return userService.deleteUser(userId);
    }
}
