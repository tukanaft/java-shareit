package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BookingMapper {
    public ru.practicum.shareit.booking.dto.BookingDto toBookingDto(Booking booking) {
        return new ru.practicum.shareit.booking.dto.BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker(),
                booking.getStatus()
        );
    }

    public BookingDtoToReturn toBookingDtoToReturn(Booking booking) {
        return new BookingDtoToReturn(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus()
        );
    }

    public Booking toBooking(BookingDto bookingDto, Item item) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                bookingDto.getBooker(),
                bookingDto.getStatus()
        );
    }

    public List<BookingDtoToReturn> bookingDtoList(List<Booking> bookings) {
        List<BookingDtoToReturn> bookingsDto = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingsDto.add(new BookingDtoToReturn(
                    booking.getId(),
                    booking.getStart(),
                    booking.getEnd(),
                    booking.getItem(),
                    booking.getBooker(),
                    booking.getStatus()
            ));
        }
        return bookingsDto;
    }
}
