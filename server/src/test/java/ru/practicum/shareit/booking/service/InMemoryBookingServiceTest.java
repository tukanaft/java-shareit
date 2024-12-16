package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
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

    @Test
    void addBooking() throws InterruptedException {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asdWEVW=0.]-0E/XRK=W-E0LRC"
        );
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
    void updateStatus() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd1 W]E[FI-0,C.W=-RCI.W0ROIC"
        );
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
    void getBooking() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd2wxw=ecrp/w.=e-o.w-0irow"
        );
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
    void getBookingByUser() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd3WER[]0,W.-0RIVW=-E0.K/CW"
        );
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
    void getBookingByOwner() {
        UserDto userDto = new UserDto(
                null,
                "name",
                "email@asd4wevr[]p-we,.=i.w/-0kxo=o"
        );
        userDto = userService.addUser(userDto);
        itemDto = itemService.addItem(userDto.getId(), itemDto);
        bookingDto.setItemId(itemDto.getId());
        bookingDto.setId(bookingService.addBooking(bookingDto, userDto.getId()).getId());
        bookingDto.setStatus(Status.WAITING);
        List<BookingDtoToReturn> bookingsActual = bookingService.getBookingByOwner(userDto.getId(), "ALL");
        Assertions.assertThat(bookingsActual.getFirst().getItem().getId()).isEqualTo(bookingDto.getItemId());
        Assertions.assertThat(bookingsActual.getFirst().getStatus()).isEqualTo(bookingDto.getStatus());
    }
}