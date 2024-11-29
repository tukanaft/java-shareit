package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
public interface ItemService {
    Item addItem(Integer ownerId, ItemDto item);

    Item updateItem(Integer ownerId, ItemDto item, Integer itemid);

    Item getItem(Integer itemId);

    List<ItemDto> getItems(Integer ownerId);

    List<ItemDto> findItems(String text);
}
