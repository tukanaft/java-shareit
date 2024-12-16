package ru.practicum.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemDtoWithBooking;

import java.util.List;

@Service
public interface ItemService {
    ItemDto addItem(Integer ownerId, ItemDto item);

    ItemDto updateItem(Integer ownerId, ItemDto item, Integer itemid);

    ItemDtoWithBooking getItem(Integer itemId);

    List<ItemDtoWithBooking> getItems(Integer ownerId);

    List<ItemDto> findItems(String text);

    CommentDto addComment(CommentDto comment, Integer itemId, Integer userId);
}
