package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoToReturn addBooking(@RequestHeader("X-Sharer-User-Id") Integer bookerId, @RequestBody BookingDto booking) {
        log.info("BookingController выполнение запроса на добавление бронирования");
        return bookingService.addBooking(booking, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoToReturn updateBookingStatus(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                                  @PathVariable Integer bookingId, @RequestParam Boolean approved) {
        log.info("BookingController выполнение запроса на обновление статуса бронирования: {}", bookingId);
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoToReturn getBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer bookingId) {
        log.info("BookingController выполнение запроса на отправление бронирования: {}", bookingId);
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDtoToReturn> getBookingByUser(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                                     @RequestParam(defaultValue = "ALL") String state) {
        log.info("BookingController выполнение запроса на отправление бронирования пользователя: {}", userId);
        return bookingService.getBookingByUser(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoToReturn> getBookingByOwner(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                                                      @RequestParam(defaultValue = "ALL") String state) {
        log.info("BookingController выполнение запроса на отправление бронирования предметов пользователя: {}", ownerId);
        return bookingService.getBookingByOwner(ownerId, state);
    }

}
