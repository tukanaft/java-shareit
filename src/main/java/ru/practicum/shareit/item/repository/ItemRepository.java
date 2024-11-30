package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;

public interface ItemRepository {
    Item addItem(Item item);

    Item updateItem(Item item, Integer itemId);

    Item getItem(Integer itemId);

    List<Item> getItemsByOwner(Integer ownerId);

    HashMap<Integer, Item> getItems();

    List<Item> findItems(String text);
}
