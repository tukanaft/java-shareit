package ru.practicum.shareit.booking.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonTest
class BookingDtoToReturnTest {

    @Autowired
    JacksonTester<BookingDtoToReturn> bookingDtoToReturnJacksonTester;

    @Test
    void bookingDtoToReturnTest() throws IOException {
        User user = new User(
                null,
                "name",
                "email@asd"
        );
        Item item = new Item(
                null,
                "name",
                "description",
                user,
                true,
                null
        );
        BookingDtoToReturn bookingDto = new BookingDtoToReturn(
                1,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now(),
                item,
                user,
                Status.WAITING
        );

        JsonContent<BookingDtoToReturn> actual = bookingDtoToReturnJacksonTester.write(bookingDto);
        Assertions.assertThat(actual).hasJsonPath("$.end");
        Assertions.assertThat(actual).hasJsonPath("$.start");
    }

}