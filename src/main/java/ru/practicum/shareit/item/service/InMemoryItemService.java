package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class InMemoryItemService implements ItemService {
    final UserRepository userRepository;
    final ItemRepository itemRepository;

    @Override
    public Item addItem(Integer ownerId, ItemDto item) {
        isUserExists(ownerId);
        validateItem(item);
        return itemRepository.addItem(item, ownerId);
    }

    @Override
    public Item updateItem(Integer ownerId, ItemDto item, Integer itemId) {
        isUserExists(ownerId);
        isUserOwner(ownerId, itemRepository.getItem(itemId));
        return itemRepository.updateItem(item, itemId);
    }

    @Override
    public Item getItem(Integer itemId) {
        return itemRepository.getItem(itemId);
    }

    @Override
    public List<ItemDto> getItems(Integer ownerId) {
        isUserExists(ownerId);
        return itemRepository.getItems(ownerId);
    }

    @Override
    public List<ItemDto> findItems(String text) {
        if (text.isEmpty()){
            return new ArrayList<>();
        }
        return itemRepository.findItems(text);
    }

    private void isUserOwner(Integer ownerId, Item item) {
        if (!Objects.equals(item.getOwner().getId(), ownerId)) {
            throw new NotFoundException("пользователь не является хозяином предмета");
        }
    }

    private void isUserExists(Integer userId) {
        if (!userRepository.isUserExists(userId)) {
            throw new NotFoundException("пользователя нет в базе");
        }
    }

    private void validateItem(ItemDto item) {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new ValidationException("не введено имя");
        }
        if (item.getDescription() == null) {
            throw new ValidationException("не введено описание");
        }
        if (item.getAvailable() == null) {
            throw new ValidationException("не введена информация о доступности");
        }
    }
}
