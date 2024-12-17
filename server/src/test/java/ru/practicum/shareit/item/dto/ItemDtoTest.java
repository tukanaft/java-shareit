package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
class ItemDtoTest {

    @Autowired
    JacksonTester<ItemDto> itemDtoDtoJacksonTester;

    @Test
    void itemDtoTest() throws IOException {
        ItemDto itemDto = new ItemDto(
                1,
                "name",
                "description",
                null,
                true,
                null
        );

        JsonContent<ItemDto> actual = itemDtoDtoJacksonTester.write(itemDto);
        Assertions.assertThat(itemDto).isEqualTo(itemDto);
    }

}