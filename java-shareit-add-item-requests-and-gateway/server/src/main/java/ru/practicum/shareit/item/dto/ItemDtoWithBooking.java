package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoWithBooking {
    Integer id;
    String name;
    String description;
    User owner;
    Boolean available;
    Request request;
    BookingDtoToReturn lastBooking;
    BookingDtoToReturn nextBooking;
    List<CommentDto> comments;
}
