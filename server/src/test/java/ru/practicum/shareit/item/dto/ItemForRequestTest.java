package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
class ItemForRequestTest {


    @Autowired
    JacksonTester<ItemForRequest> itemDtoDtoJacksonTester;

    @Test
    void commentDtoTest() throws IOException {
        ItemForRequest itemDto = new ItemForRequest(
                1,
                "name",
                1
        );

        JsonContent<ItemForRequest> actual = itemDtoDtoJacksonTester.write(itemDto);
        Assertions.assertThat(itemDto).isEqualTo(itemDto);
    }

}