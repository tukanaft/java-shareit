package ru.practicum.shareit.item.repository;


import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryItemRepository implements ItemRepository {

    private HashMap<Integer, Item> items;
    private Integer itemId;

    public InMemoryItemRepository() {
        items = new HashMap<>();
        itemId = 1;
    }

    @Override
    public Item addItem(Item item) {
        item.setId(itemId);
        items.put(item.getId(), item);
        itemId++;
        return item;
    }

    @Override
    public Item updateItem(Item item, Integer itemId) {
        if (item.getName() != null) {
            items.get(itemId).setName(item.getName());
        }
        if (item.getDescription() != null) {
            items.get(itemId).setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            items.get(itemId).setAvailable(item.getAvailable());
        }
        return items.get(itemId);
    }

    @Override
    public Item getItem(Integer itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> getItemsByOwner(Integer ownerId) {
        List<Item> itemsToSend = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getOwner().getId().equals(ownerId)) {
                itemsToSend.add(item);
            }
        }

        return itemsToSend;
    }

    @Override
    public HashMap<Integer, Item> getItems() {
        return items;
    }

    @Override
    public List<Item> findItems(String text) {
        List<Item> itemsToSend = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getName().toUpperCase().contains(text.toUpperCase()) ||
                    item.getDescription().toUpperCase().contains(text.toUpperCase())) {
                itemsToSend.add(item);
            }
        }

        return itemsToSend;
    }
}
