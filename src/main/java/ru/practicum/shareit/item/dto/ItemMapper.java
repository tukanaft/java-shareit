package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    final UserRepository userRepository;

    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                item.getRequest()
        );
    }

    public Item toItem(ItemDto item, User owner) {
        return new Item(
                item.getId(),
                item.getName(),
                item.getDescription(),
                owner,
                item.getAvailable(),
                item.getRequest()
        );
    }

    public List<ItemDto> itemDtoList(List<Item> items) {
        List<ItemDto> itemsDto = new ArrayList<>();
        for (Item item : items) {
            itemsDto.add(new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getOwner(),
                    item.getAvailable(),
                    item.getRequest()
            ));
        }
        return itemsDto;
    }
}
