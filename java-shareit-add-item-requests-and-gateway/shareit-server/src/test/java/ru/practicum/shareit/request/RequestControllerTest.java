package ru.practicum.shareit.request;

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
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoWithItem;
import ru.practicum.shareit.request.service.RequestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(RequestController.class)
class RequestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private RequestService requestService;

    RequestDto requestDto = new RequestDto(
            null,
            "description",
            null
    );
    RequestDto requestMock = new RequestDto(
            1,
            "description",
            LocalDateTime.now().minusHours(2)
    );
    Integer authorId = 1;
    RequestDtoWithItem requestMockWithItem = new RequestDtoWithItem(
            1,
            "description",
            null,
            null
    );

    @Test
    void addRequest() throws Exception {
        Mockito.when(requestService.addRequest(authorId, requestDto)).thenReturn(requestMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/request")
                        .header("X-Sharer-User-Id", authorId)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(requestMock));
    }

    @Test
    void getUserRequests() throws Exception {
        List<RequestDtoWithItem> items = new ArrayList<>();
        items.add(requestMockWithItem);
        Mockito.when(requestService.getUserRequests(authorId)).thenReturn(items);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/request")
                        .header("X-Sharer-User-Id", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(items));
    }

    @Test
    void getAllRequests() throws Exception {
        List<RequestDto> items = new ArrayList<>();
        items.add(requestMock);
        Mockito.when(requestService.getAllRequests()).thenReturn(items);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/request/all")
                        .header("X-Sharer-User-Id", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(items));
    }

    @Test
    void getRequestById() throws Exception {
        Mockito.when(requestService.getRequestById(requestMock.getId())).thenReturn(requestMockWithItem);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/request/1")
                        .header("X-Sharer-User-Id", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(requestMockWithItem));
    }
}