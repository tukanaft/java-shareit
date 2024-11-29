package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;

public interface ItemRepository {
    Boolean addItem(ItemDto item, Integer ownerId);

    ItemDto updateItem(ItemDto item, Integer itemId);

    ItemDto getItem(Integer itemId);

    List<ItemDto> getItems(Integer ownerId);

    List<ItemDto> findItems(String text);
}
