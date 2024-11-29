package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getIsAvailable(),
                item.getRequest()
        );
    }

    public Item toItem(Integer itemId, ItemDto item) {
        return new Item(
                itemId,
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getIsAvailable(),
                item.getRequest()
        );
    }
}
