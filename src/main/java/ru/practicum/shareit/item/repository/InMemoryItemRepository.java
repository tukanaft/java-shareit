package ru.practicum.shareit.item.repository;


import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.Repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryItemRepository implements ItemRepository {

    private HashMap<Integer, Item> items;
    private Integer itemId;
    UserRepository userRepository;
    ItemMapper itemMapper;

    public InMemoryItemRepository(ItemMapper itemMapper) {
        items = new HashMap<>();
        itemId = 1;
        this.itemMapper = itemMapper;
    }

    @Override
    public Boolean addItem(ItemDto item, Integer ownerId) {
        Item itemSave = itemMapper.toItem(ownerId, item);
        items.put(itemSave.getId(), itemSave);
        itemId++;
        return true;
    }

    @Override
    public ItemDto updateItem(ItemDto item, Integer itemId) {
        if (item.getName() != null) {
            items.get(itemId).setName(item.getName());
        }
        if (item.getDescription() != null) {
            items.get(itemId).setDescription(item.getDescription());
        }
        if (item.getIsAvailable() != null) {
            items.get(itemId).setIsAvailable(item.getIsAvailable());
        }
        return itemMapper.toItemDto(items.get(itemId));
    }

    @Override
    public ItemDto getItem(Integer itemId) {
        return itemMapper.toItemDto(items.get(itemId));
    }

    @Override
    public List<ItemDto> getItems(Integer ownerId) {
        List<ItemDto> itemsToSend = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getOwner().getId().equals(ownerId)) {
                itemsToSend.add(itemMapper.toItemDto(item));
            }
        }

        return itemsToSend;
    }

    @Override
    public List<ItemDto> findItems(String text) {
        List<ItemDto> itemsToSend = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getName().contains(text)) {
                itemsToSend.add(itemMapper.toItemDto(item));
            } else if (item.getDescription().contains(text)) {
                itemsToSend.add(itemMapper.toItemDto(item));
            }
        }
        return itemsToSend;
    }
}
