package test.java.ru.practicum.shareit.request.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonTest
class RequestDtoTest {

    @Autowired
    JacksonTester<RequestDto> requestDtoJacksonTester;

    @Test
    void bookingDtoTest() throws IOException {
        RequestDto requestDto = new RequestDto(
                1,
                "description",
                LocalDateTime.now()
        );

        JsonContent<RequestDto> actual = requestDtoJacksonTester.write(requestDto);
        Assertions.assertThat(actual).hasJsonPath("$.created");
    }
}