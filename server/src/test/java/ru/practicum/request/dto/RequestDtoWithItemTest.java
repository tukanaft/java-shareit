package ru.practicum.request.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonTest
class RequestDtoWithItemTest {

    @Autowired
    JacksonTester<RequestDtoWithItem> requestDtoWithItemJacksonTester;

    @Test
    void bookingDtoTest() throws IOException {
        RequestDtoWithItem requestDtoWithItem = new RequestDtoWithItem(
                1,
                "description",
                LocalDateTime.now(),
                null
        );

        JsonContent<RequestDtoWithItem> actual = requestDtoWithItemJacksonTester.write(requestDtoWithItem);
        Assertions.assertThat(actual).hasJsonPath("$.created");
    }
}