package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@Sql(scripts = {"file:src/main/resources/schema.sql"})
class InMemoryItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    ItemDto itemDto = new ItemDto(
            null,
            "name",
            "description",
            null,
            true,
            null
    );

    @AfterEach
    void clearDb() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addItem() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asdcA0ER,-FSA=ET9UAER,TVK.0G."
        );
        userDto = userService.addUser(userDto);
        ItemDto saveItem = itemService.addItem(userDto.getId(), itemDto);
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void updateItem() {
        UserDto userDto = new UserDto(
                null,
                "name1",
                "email@asd1wce]r9,,,,,t=.g]0oka.]-d0f"
        );
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        itemDto.setName("new name");
        itemDto.setDescription("new description");
        itemDto.setAvailable(false);
        ItemDto saveItem = itemService.updateItem(userDto.getId(), itemDto, itemDto.getId());
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void getItem() {
        UserDto userDto = new UserDto(
                null,
                "name2",
                "email@asd2XWERFOJ,]QWM-0TCVJ.KA/.[CF"
        );
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        ItemDtoWithBooking saveItem = itemService.getItem(itemDto.getId());
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void getItems() {
        UserDto userDto = new UserDto(
                null,
                "name3",
                "email@asd3wxc09],=wt8j,sa[dpkoc.xf=-we0o"
        );
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        List<ItemDtoWithBooking> items = itemService.getItems(itemDto.getOwner().getId());
        Assertions.assertThat(items.getFirst().getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(items.getFirst().getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(items.getFirst().getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(items.getFirst().getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void findItems() {
        UserDto userDto = new UserDto(
                null,
                "name4",
                "email@asd4WXER][O,0xwero,er09[u=vcw,"
        );
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        List<ItemDtoWithBooking> items = itemService.getItems(userDto.getId());
        Assertions.assertThat(items.getFirst().getName()).isEqualTo(items.getFirst().getName());
        Assertions.assertThat(items.getFirst().getDescription()).isEqualTo(items.getFirst().getDescription());
        Assertions.assertThat(items.getFirst().getOwner().getId()).isEqualTo(items.getFirst().getId());
        Assertions.assertThat(items.getFirst().getAvailable()).isEqualTo(items.getFirst().getAvailable());
    }
}