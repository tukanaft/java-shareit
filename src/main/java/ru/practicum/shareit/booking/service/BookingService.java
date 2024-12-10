package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;

import java.util.List;

public interface BookingService {

    BookingDtoToReturn addBooking(BookingDto booking, Integer bookerId);

    BookingDtoToReturn updateStatus(Integer userId, Integer bookingId, Boolean status);

    BookingDtoToReturn getBooking(Integer bookingID, Integer userId);

    List<BookingDtoToReturn> getBookingByUser(Integer userId, String state);

    List<BookingDtoToReturn> getBookingByOwner(Integer ownerId, String state);
}
