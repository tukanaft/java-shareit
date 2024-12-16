package ru.practicum.booking.service;

import org.springframework.stereotype.Service;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingDtoToReturn;

import java.util.List;

@Service
public interface BookingService {

    BookingDtoToReturn addBooking(BookingDto booking, Integer bookerId);

    BookingDtoToReturn updateStatus(Integer userId, Integer bookingId, Boolean status);

    BookingDtoToReturn getBooking(Integer bookingID, Integer userId);

    List<BookingDtoToReturn> getBookingByUser(Integer userId, String state);

    List<BookingDtoToReturn> getBookingByOwner(Integer ownerId, String state);
}
