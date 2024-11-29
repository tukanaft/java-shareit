package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.Repository.UserRepository;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    final private UserRepository userRepository;

    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                item.getRequest()
        );
    }

    public Item toItem(Integer itemId, ItemDto item, Integer ownerId) {
        return new Item(
                itemId,
                item.getName(),
                item.getDescription(),
                userRepository.getUser(ownerId),
                item.getAvailable(),
                item.getRequest()
        );
    }
}
