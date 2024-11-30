package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

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
    final ItemMapper itemMapper;

    @Override
    public ItemDto addItem(Integer ownerId, ItemDto item) {
        log.info("ItemService выполнение запроса на добавление предмета");
        isUserExists(ownerId);
        validateItem(item);
        return itemMapper.toItemDto(itemRepository.addItem(itemMapper.toItem(item, userRepository.getUser(ownerId))));
    }

    @Override
    public ItemDto updateItem(Integer ownerId, ItemDto item, Integer itemId) {
        log.info("ItemService выполнение запроса на обновление предмета: {}", itemId);
        isUserExists(ownerId);
        isItemExists(itemId);
        isUserOwner(ownerId, itemMapper.toItemDto(itemRepository.getItem(itemId)));
        item.setId(itemId);
        return itemMapper.toItemDto(itemRepository.updateItem(itemMapper.toItem(item, userRepository.getUser(ownerId)), itemId));
    }

    @Override
    public ItemDto getItem(Integer itemId) {
        log.info("ItemService выполнение запроса на отправление предмета: {}", itemId);
        isItemExists(itemId);
        return itemMapper.toItemDto(itemRepository.getItem(itemId));
    }

    @Override
    public List<ItemDto> getItems(Integer ownerId) {
        log.info("ItemService выполнение запроса на отправление всех предметов:");
        isUserExists(ownerId);
        return itemMapper.itemDtoList(itemRepository.getItemsByOwner(ownerId));
    }

    @Override
    public List<ItemDto> findItems(String text) {
        log.info("ItemService выполнение запроса на поиск предмета предмета");
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemMapper.itemDtoList(itemRepository.findItems(text));
    }

    private void isUserOwner(Integer ownerId, ItemDto item) {
        if (!Objects.equals(item.getOwner().getId(), ownerId)) {
            throw new NotFoundException("пользователь не является хозяином предмета");
        }
    }

    private void isUserExists(Integer userId) {
        if (!userRepository.isUserExists(userId)) {
            throw new NotFoundException("пользователя c id " + userId + "нет в базе");
        }
    }

    private void isItemExists(Integer itemId) {
        if (!itemRepository.getItems().containsKey(itemId)) {
            throw new NotFoundException("предмета c id " + itemId + "нет в базе");
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
