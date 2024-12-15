package ru.practicum.shareit.user;

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
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    UserDto userDto = new UserDto(
            null,
            "name",
            "email@email"
    );
    UserDto userMock = new UserDto(
            1,
            "name",
            "email@email"
    );

    @Test
    void addUser() throws Exception {
        Mockito.when(userService.addUser(userDto)).thenReturn(userMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(userMock));
    }

    @Test
    void updateUser() throws Exception {
        Mockito.when(userService.updateUser(userDto, 1)).thenReturn(userMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.patch("/users/1")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(userMock));
    }

    @Test
    void getUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(userDto);
        List<UserDto> usersMock = new ArrayList<>();
        usersMock.add(userMock);
        Mockito.when(userService.getUsers()).thenReturn(usersMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .content(objectMapper.writeValueAsString(users))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(usersMock));
    }

    @Test
    void getUser() throws Exception {
        Mockito.when(userService.getUser(1)).thenReturn(userMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(userMock));
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.when(userService.deleteUser(1)).thenReturn(true);
        String actual = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(true));
    }
}