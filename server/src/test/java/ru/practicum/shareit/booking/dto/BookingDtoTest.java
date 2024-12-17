package ru.practicum.shareit.booking.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.user.model.User;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonTest
class BookingDtoTest {

    @Autowired
    JacksonTester<BookingDto> bookingDtoJacksonTester;

    @Test
    void bookingDtoTest() throws IOException {
        User user = new User(
                null,
                "name",
                "email@asd"
        );
        BookingDto bookingDto = new BookingDto(
                1,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now(),
                1,
                user,
                Status.WAITING
        );

        JsonContent<BookingDto> actual = bookingDtoJacksonTester.write(bookingDto);
        Assertions.assertThat(actual).hasJsonPath("$.end");
        Assertions.assertThat(actual).hasJsonPath("$.start");
    }
}