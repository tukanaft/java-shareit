package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@Sql(scripts = {"file:src/main/resources/schema.sql"})
class InMemoryBookingServiceTest {
    @Autowired
    BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;


    BookingDto bookingDto = new BookingDto(
            null,
            LocalDateTime.now().plusMinutes(2),
            LocalDateTime.now().plusMinutes(6),
            null,
            null,
            null
    );

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

    @AfterEach
    void clearDb() {
        bookingRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addBooking() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        BookingDtoToReturn bookingActual = bookingService.addBooking(bookingDto, userDto.getId());
        bookingDto.setStatus(Status.WAITING);
        Assertions.assertThat(bookingActual.getStart()).isEqualTo(bookingDto.getStart());
        Assertions.assertThat(bookingActual.getEnd()).isEqualTo(bookingDto.getEnd());
        Assertions.assertThat(bookingActual.getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingActual.getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void addBookingUserNotFound() {
        Assertions.assertThatThrownBy(() -> bookingService.addBooking(bookingDto, 123))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void addBookingValidationFail() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setEnd(LocalDateTime.now().minusWeeks(1));
        Assertions.assertThatThrownBy(() -> bookingService.addBooking(bookingDto, userDto.getId()))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void addBookingItemNotFound() {
        userDto = userService.addUser(userDto);
        bookingDto.setItemId(123);
        Assertions.assertThatThrownBy(() -> bookingService.addBooking(bookingDto, userDto.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateStatus() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        BookingDtoToReturn bookingActual = bookingService.updateStatus(userDto.getId(), bookingDto.getId(), true);
        bookingDto.setStatus(Status.APPROVED);
        Assertions.assertThat(bookingActual.getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingActual.getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void updateStatusRejected() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        BookingDtoToReturn bookingActual = bookingService.updateStatus(userDto.getId(), bookingDto.getId(), false);
        bookingDto.setStatus(Status.REJECTED);
        Assertions.assertThat(bookingActual.getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingActual.getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void updateStatusValidationFail() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        BookingDtoToReturn bookingDto2 = bookingService.addBooking(bookingDto, userDto.getId());
        Assertions.assertThatThrownBy(() -> bookingService.updateStatus(123, bookingDto2.getId(), true))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void getBooking() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        BookingDtoToReturn bookingActual = bookingService.getBooking(bookingDto.getId(), userDto.getId());
        Assertions.assertThat(bookingActual.getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingActual.getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingUserNotFound() {
        Assertions.assertThatThrownBy(() -> bookingService.getBookingByUser(123, "ALL"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getBookingByUser() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByUser(userDto.getId(), "ALL");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByUserCurrent() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(12));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        Thread.sleep(6000);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByUser(userDto.getId(), "CURRENT");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByUserPast() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(LocalDateTime.now().plusSeconds(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        Thread.sleep(6000);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByUser(userDto.getId(), "PAST");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByUserFuture() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByUser(userDto.getId(), "FUTURE");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByUserWaiting() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByUser(userDto.getId(), "WAITING");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByUserRejected() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingService.updateStatus(userDto.getId(), bookingDto.getId(), false);
        bookingDto.setStatus(Status.REJECTED);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByUser(userDto.getId(), "REJECTED");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByOwner() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByOwner(userDto.getId(), "ALL");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingOwnerCurrent() throws InterruptedException {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusSeconds(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(12));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        Thread.sleep(6000);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByOwner(userDto.getId(), "CURRENT");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }


    @Test
    void getBookingByOwnerFuture() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByOwner(userDto.getId(), "FUTURE");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByOwnerWaiting() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByOwner(userDto.getId(), "WAITING");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingByOwnerRejected() {
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(3));
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingService.updateStatus(userDto.getId(), bookingDto.getId(), false);
        bookingDto.setStatus(Status.REJECTED);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByOwner(userDto.getId(), "REJECTED");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }

    @Test
    void getBookingOwnerNotFound() {
        Assertions.assertThatThrownBy(() -> bookingService.getBookingByOwner(123, "ALL"))
                .isInstanceOf(NotFoundException.class);
    }
}