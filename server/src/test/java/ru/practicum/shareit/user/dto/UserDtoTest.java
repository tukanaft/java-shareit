package ru.practicum.shareit.user.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
class UserDtoTest {
    @Autowired
    JacksonTester<UserDto> userDtoJacksonTester;

    @Test
    void bookingDtoTest() throws IOException {
        UserDto userDto = new UserDto(
                1,
                "name",
                "email@email"
        );

        JsonContent<UserDto> actual = userDtoJacksonTester.write(userDto);
        Assertions.assertThat(userDto.getName()).isEqualTo("name");
    }

}