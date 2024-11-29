package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addItem(ItemDto item, Integer ownerId);

    Item updateItem(ItemDto item, Integer itemId);

    Item getItem(Integer itemId);

    List<ItemDto> getItems(Integer ownerId);

    List<ItemDto> findItems(String text);
}
