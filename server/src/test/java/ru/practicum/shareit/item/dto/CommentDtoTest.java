package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.CommentDto;

import java.io.IOException;

@JsonTest
class CommentDtoTest {


    @Autowired
    JacksonTester<CommentDto> commentDtoDtoJacksonTester;

    @Test
    void bookingDtoTest() throws IOException {
        CommentDto commentDto = new CommentDto(
                1,
                "text",
                "auhorName",
                null,
                null
        );

        JsonContent<CommentDto> actual = commentDtoDtoJacksonTester.write(commentDto);
        Assertions.assertThat(actual).hasJsonPath("$.created");
    }

}