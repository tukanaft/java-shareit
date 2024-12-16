package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    ItemDto itemDto = new ItemDto(
            null,
            "name",
            "description",
            null,
            true,
            null
    );
    ItemDtoWithBooking itemDtoBooking = new ItemDtoWithBooking(
            null,
            "name",
            "description",
            null,
            true,
            null,
            null,
            null,
            null
    );
    User user = new User(
            1,
            "name",
            "email@asd"
    );
    ItemDto itemMock = new ItemDto(
            1,
            "name",
            "description",
            user,
            true,
            null
    );
    ItemDtoWithBooking itemMockBooking = new ItemDtoWithBooking(
            1,
            "name",
            "description",
            user,
            true,
            null,
            null,
            null,
            null
    );
    Integer ownerId = 1;

    @Test
    void addItem() throws Exception {
        Mockito.when(itemService.addItem(ownerId, itemDto)).thenReturn(itemMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .header("X-Sharer-User-Id", ownerId)
                        .content(objectMapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(itemMock));
    }

    @Test
    void updateItem() throws Exception {
        Mockito.when(itemService.updateItem(ownerId, itemDto, 1)).thenReturn(itemMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.patch("/items/1")
                        .header("X-Sharer-User-Id", ownerId)
                        .content(objectMapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(itemMock));
    }

    @Test
    void getItem() throws Exception {
        Mockito.when(itemService.getItem(ownerId)).thenReturn(itemMockBooking);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/items/1")
                        .header("X-Sharer-User-Id", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(itemMockBooking));
    }

    @Test
    void getItems() throws Exception {
        List<ItemDtoWithBooking> itemsMock = new ArrayList<>();
        itemsMock.add(itemMockBooking);
        Mockito.when(itemService.getItems(ownerId)).thenReturn(itemsMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .header("X-Sharer-User-Id", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(itemsMock));
    }

    @Test
    void findItems() throws Exception {
        List<ItemDto> itemsMock = new ArrayList<>();
        itemsMock.add(itemMock);
        String text = "name";
        Mockito.when(itemService.findItems(text)).thenReturn(itemsMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/items/search")
                        .header("X-Sharer-User-Id", ownerId)
                        .param("text", text)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(itemsMock));
    }

    @Test
    void addComment() throws Exception {

        CommentDto commentDto = new CommentDto(
                null,
                "text",
                "name",
                itemDto,
                LocalDateTime.now().minusHours(1)
        );
        CommentDto commentMock = new CommentDto(
                1,
                "text",
                "name",
                itemDto,
                LocalDateTime.now().minusHours(1)
        );
        Mockito.when(itemService.addComment(commentDto, 1, 1)).thenReturn(commentMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/items/1/comment")
                        .header("X-Sharer-User-Id", ownerId)
                        .content(objectMapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(commentMock));
    }
}