package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
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

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CommentRepository commentRepository;

    ItemDto itemDto = new ItemDto(
            null,
            "name",
            "description",
            null,
            true,
            null
    );

    UserDto userDto = new UserDto(
            null,
            "name",
            "email@asd"
    );

    RequestDto requestDto = new RequestDto(
            null,
            "description",
            null
    );
    BookingDto bookingDto = new BookingDto(
            null,
            LocalDateTime.now().plusSeconds(1),
            LocalDateTime.now().plusSeconds(2),
            null,
            null,
            null
    );
    CommentDto commentDto = new CommentDto(
            null,
            "null",
            null,
            null,
            null
    );

    @AfterEach
    void clearDb() {
        commentRepository.deleteAll();
        bookingRepository.deleteAll();
        itemRepository.deleteAll();
        requestRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void addItem() {
        userDto = userService.addUser(userDto);
        ItemDto saveItem = itemService.addItem(userDto.getId(), itemDto);
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void addItemWithRequest() {
        userDto = userService.addUser(userDto);
        requestDto = requestService.addRequest(userDto.getId(), requestDto);
        ItemDto saveItem = itemService.addItem(userDto.getId(), itemDto);
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
        Assertions.assertThat(saveItem.getRequestId()).isEqualTo(null);
    }

    @Test
    void addItemNoUser() {
        Assertions.assertThatThrownBy(() -> itemService.addItem(123, itemDto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateItem() {
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
    void updateItemUserNotFound() {
        Assertions.assertThatThrownBy(() -> itemService.updateItem(123, itemDto, 123))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateItemItemNotFound() {
        userDto = userService.addUser(userDto);
        Assertions.assertThatThrownBy(() -> itemService.updateItem(userDto.getId(), itemDto, 123))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateItemName() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        itemDto.setName("new name");
        itemDto.setDescription(null);
        itemDto.setAvailable(null);
        ItemDto saveItem = itemService.updateItem(userDto.getId(), itemDto, itemDto.getId());
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
    }

    @Test
    void updateItemDescription() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        itemDto.setName(null);
        itemDto.setDescription("new description");
        itemDto.setAvailable(null);
        ItemDto saveItem = itemService.updateItem(userDto.getId(), itemDto, itemDto.getId());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
    }

    @Test
    void updateItemAvailable() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        itemDto.setName(null);
        itemDto.setDescription(null);
        itemDto.setAvailable(false);
        ItemDto saveItem = itemService.updateItem(userDto.getId(), itemDto, itemDto.getId());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void getItem() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        ItemDtoWithBooking saveItem = itemService.getItem(itemDto.getId());
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void getItemItemNotFound() {
        userDto = userService.addUser(userDto);
        Assertions.assertThatThrownBy(() -> itemService.getItem(123))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getItemWithComment() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(bookingDto.getStart().plusSeconds(2));
        bookingService.addBooking(bookingDto, userDto.getId());
        Thread.sleep(6000);
        commentDto = itemService.addComment(commentDto, itemDto.getId(), userDto.getId());
        ItemDtoWithBooking saveItem = itemService.getItem(itemDto.getId());
        Assertions.assertThat(saveItem.getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(saveItem.getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(saveItem.getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(saveItem.getAvailable()).isEqualTo(itemDto.getAvailable());
        Assertions.assertThat(saveItem.getComments().getFirst().getId()).isEqualTo(commentDto.getId());
    }

    @Test
    void getItems() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        List<ItemDtoWithBooking> items = itemService.getItems(itemDto.getOwner().getId());
        Assertions.assertThat(items.getFirst().getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(items.getFirst().getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(items.getFirst().getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(items.getFirst().getAvailable()).isEqualTo(itemDto.getAvailable());
    }

    @Test
    void getItemsWithComment() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(bookingDto.getStart().plusSeconds(2));
        bookingService.addBooking(bookingDto, userDto.getId());
        Thread.sleep(6000);
        commentDto = itemService.addComment(commentDto, itemDto.getId(), userDto.getId());
        List<ItemDtoWithBooking> items = itemService.getItems(itemDto.getOwner().getId());
        Assertions.assertThat(items.getFirst().getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(items.getFirst().getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(items.getFirst().getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(items.getFirst().getAvailable()).isEqualTo(itemDto.getAvailable());
        Assertions.assertThat(items.getFirst().getComments().getFirst().getId()).isEqualTo(commentDto.getId());
    }

    @Test
    void getItemsWithBookingAndComment() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(bookingDto.getStart().plusSeconds(2));
        bookingService.addBooking(bookingDto, userDto.getId());
        Thread.sleep(6000);
        commentDto = itemService.addComment(commentDto, itemDto.getId(), userDto.getId());
        List<ItemDtoWithBooking> items = itemService.getItems(itemDto.getOwner().getId());
        Assertions.assertThat(items.getFirst().getName()).isEqualTo(itemDto.getName());
        Assertions.assertThat(items.getFirst().getDescription()).isEqualTo(itemDto.getDescription());
        Assertions.assertThat(items.getFirst().getOwner().getId()).isEqualTo(userDto.getId());
        Assertions.assertThat(items.getFirst().getAvailable()).isEqualTo(itemDto.getAvailable());
        Assertions.assertThat(items.getFirst().getComments().getFirst().getId()).isEqualTo(commentDto.getId());
        Assertions.assertThat(items.getFirst().getLastBooking().getItem().getId()).isEqualTo(itemDto.getId());
    }

    @Test
    void getItemsUserNotFound() {
        Assertions.assertThatThrownBy(() -> itemService.getItems(123))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    void findItems() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        List<ItemDtoWithBooking> items = itemService.getItems(userDto.getId());
        Assertions.assertThat(itemDto.getName()).isEqualTo("name");
    }

    @Test
    void addComment() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(bookingDto.getStart().plusSeconds(2));
        bookingService.addBooking(bookingDto, userDto.getId());
        Thread.sleep(6000);
        CommentDto actual = itemService.addComment(commentDto, itemDto.getId(), userDto.getId());
        Assertions.assertThat(actual.getText()).isEqualTo(commentDto.getText());
    }

    @Test
    void addCommentUserNotFound() {
        Assertions.assertThatThrownBy(() -> itemService.addComment(commentDto, 123, 123))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void addCommentItemNotFound() {
        userDto = userService.addUser(userDto);
        Assertions.assertThatThrownBy(() -> itemService.addComment(commentDto, 123, userDto.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void addCommentValidationFail() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(bookingDto.getStart().plusMinutes(10));
        bookingService.addBooking(bookingDto, userDto.getId());
        Assertions.assertThatThrownBy(() -> itemService.addComment(commentDto, itemDto.getId(), userDto.getId()))
                .isInstanceOf(ValidationException.class);
    }
}