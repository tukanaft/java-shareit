package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
class ItemDtoWithBookingTest {


    @Autowired
    JacksonTester<ItemDtoWithBooking> itemDtoDtoJacksonTester;

    @Test
    void commentDtoTest() throws IOException {
        ItemDtoWithBooking itemDto = new ItemDtoWithBooking(
                1,
                "name",
                "description",
                null,
                null,
                null,
                null,
                null,
                null
        );

        JsonContent<ItemDtoWithBooking> actual = itemDtoDtoJacksonTester.write(itemDto);
        Assertions.assertThat(itemDto).isEqualTo(itemDto);
    }
}