package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoWithoutBooking {
    Integer id;
    String name;
    String description;
    User owner;
    Boolean available;
    ItemRequest request;
    List<CommentDto> comments;
}

