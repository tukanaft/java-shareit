package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
public interface ItemService {
    Boolean addItem(Integer ownerId, ItemDto item);

    ItemDto updateItem(Integer ownerId, ItemDto item, Integer itemid);

    ItemDto getItem(Integer itemId);

    List<ItemDto> getItems(Integer ownerId);

    List<ItemDto> findItems(String text);
}
