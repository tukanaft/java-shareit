package test.java.ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoWithItem;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@Sql(scripts = {"file:src/main/resources/schema.sql"})
class InMemoryRequestServiceTest {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    RequestDto requestDto = new RequestDto(
            null,
            "description",
            null
    );

    @Test
    void addRequest() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd"
        );
        userDto = userService.addUser(userDto);
        RequestDto saveRequest = requestService.addRequest(userDto.getId(), requestDto);
        Assertions.assertThat(saveRequest.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    void getUserRequests() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd1"
        );
        userDto = userService.addUser(userDto);
        requestDto = requestService.addRequest(userDto.getId(), requestDto);
        ItemDto itemDto = new ItemDto(
                null,
                "item",
                "description",
                null,
                true,
                requestDto.getId()

        );
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        List<RequestDtoWithItem> requestDtoWithItems = requestService.getUserRequests(userDto.getId());
        Assertions.assertThat(requestDtoWithItems.getFirst().getDescription()).isEqualTo(requestDto.getDescription());
        Assertions.assertThat(requestDtoWithItems.getFirst().getItems().getFirst().getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(requestDtoWithItems.getFirst().getItems().getFirst().getOwnerId()).isEqualTo(userDto.getId());
    }

    @Test
    void getAllRequests() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd2"
        );
        UserDto userDto2 = new UserDto(
                null,
                "name",
                "email@asd10"
        );
        userDto = userService.addUser(userDto);
        userDto2 = userService.addUser(userDto2);
        requestDto = requestService.addRequest(userDto.getId(), requestDto);
        List<RequestDto> requests = requestService.getAllRequests(userDto2.getId());
        Assertions.assertThat(requests.getFirst().getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    void getRequestById() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd3"
        );
        userDto = userService.addUser(userDto);
        requestDto = requestService.addRequest(userDto.getId(), requestDto);
        RequestDtoWithItem saveRequest = requestService.getRequestById(requestDto.getId());
        Assertions.assertThat(saveRequest.getDescription()).isEqualTo(requestDto.getDescription());
    }
}