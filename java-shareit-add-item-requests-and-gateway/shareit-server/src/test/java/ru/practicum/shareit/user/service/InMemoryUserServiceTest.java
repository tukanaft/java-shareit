package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@Sql(scripts = {"file:src/main/resources/schema.sql"})
class InMemoryUserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    UserDto userDto = new UserDto(
            null,
            "name",
            "email@asd"
    );

    @AfterEach
    void clearDb() {
        userRepository.deleteAll();
    }

    @Test
    void addUser() {
        UserDto saveUser = userService.addUser(userDto);
        Assertions.assertThat(saveUser.getName()).isEqualTo(userDto.getName());
        Assertions.assertThat(saveUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void updateUser() {
        userDto = userService.addUser(userDto);
        userDto.setEmail("mem@ddd");
        userDto.setName("grisha");
        UserDto saveUser = userService.updateUser(userDto, userDto.getId());
        Assertions.assertThat(saveUser.getName()).isEqualTo(userDto.getName());
        Assertions.assertThat(saveUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    void updateUserName() {
        userDto = userService.addUser(userDto);
        userDto.setEmail(null);
        userDto.setName("grisha");
        UserDto saveUser = userService.updateUser(userDto, userDto.getId());
        Assertions.assertThat(saveUser.getName()).isEqualTo(userDto.getName());
        Assertions.assertThat(saveUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    void updateUserEmail() {
        userDto = userService.addUser(userDto);
        userDto.setEmail("mem@ddd");
        userDto.setName(null);
        UserDto saveUser = userService.updateUser(userDto, userDto.getId());
        Assertions.assertThat(saveUser.getName()).isEqualTo(userDto.getName());
        Assertions.assertThat(saveUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void getUsers() {
        userDto = userService.addUser(userDto);
        List<UserDto> savedUsers = userService.getUsers();
        Assertions.assertThat(savedUsers.contains(userDto)).isEqualTo(true);
    }

    @Test
    void getUser() {
        userDto = userService.addUser(userDto);
        UserDto saveUser = userService.getUser(userDto.getId());
        Assertions.assertThat(saveUser.getName()).isEqualTo(userDto.getName());
        Assertions.assertThat(saveUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void deleteUser() {
        userDto = userService.addUser(userDto);
        userService.deleteUser(userDto.getId());

    }
}