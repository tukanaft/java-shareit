package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
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
    final BookingRepository bookingRepository;
    final CommentRepository commentRepository;
    final CommentMapper commentMapper;
    final BookingMapper bookingMapper;
    final RequestRepository requestRepository;

    @Override
    public ItemDto addItem(Integer ownerId, ItemDto item) {
        log.info("ItemService выполнение запроса на добавление предмета");
        User user = userRepository.findById(ownerId).orElseThrow(() -> new NotFoundException("пользователя c id " + ownerId + "нет в базе"));
        Integer requestId = item.getRequestId();
        if (item.getRequestId() != null) {
            return itemMapper.toItemDtoIfRequest(itemRepository.save(itemMapper.toItem(item, user, requestRepository.findById(requestId)
                    .orElseThrow(() -> new NotFoundException("реквеста c id " + requestId + "нет в базе")))));
        } else {
            return itemMapper.toItemDto(itemRepository.save(itemMapper.toItem(item, user, null)));
        }
    }

    @Override
    public ItemDto updateItem(Integer ownerId, ItemDto item, Integer itemId) {
        log.info("ItemService выполнение запроса на обновление предмета: {}", itemId);
        isUserExists(ownerId);
        isItemExists(itemId);
        Item itemToUpdate = itemRepository.findById(itemId).get();
        isUserOwner(ownerId, itemMapper.toItemDto(itemToUpdate));
        if (item.getName() != null) {
            itemToUpdate.setName(item.getName());
        }
        if (item.getDescription() != null) {
            itemToUpdate.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            itemToUpdate.setAvailable(item.getAvailable());
        }

        return itemMapper.toItemDto(itemRepository.save(itemToUpdate));
    }

    @Override
    public ItemDtoWithBooking getItem(Integer itemId) {
        log.info("ItemService выполнение запроса на отправление предмета: {}", itemId);
        isItemExists(itemId);
        Item item = itemRepository.findById(itemId).get();
        List<BookingDtoToReturn> bookings = bookingMapper.bookingDtoList(bookingRepository.findAllByItem_id(item.getId()));
        if (!bookings.isEmpty()) {
            if (bookings.size() == 1){
                return itemMapper.itemToItemDtoWithBooking(item, bookings.getFirst(), null,
                        commentMapper.toCommentDtoList(commentRepository.findAllByItem_Id(item.getId())));
            }
            else {
                return itemMapper.itemToItemDtoWithBooking(item, bookings.get(0), bookings.get(1),
                        commentMapper.toCommentDtoList(commentRepository.findAllByItem_Id(item.getId())));
            }
        } else {
            return itemMapper.itemToItemDtoWithBooking(item, null, null,
                    commentMapper.toCommentDtoList(commentRepository.findAllByItem_Id(item.getId())));
        }


    }

    @Override
    public List<ItemDtoWithBooking> getItems(Integer ownerId) {
        log.info("ItemService выполнение запроса на отправление всех предметов, одного пользователя:");
        isUserExists(ownerId);
        List<ItemDtoWithBooking> items = new ArrayList<>();
        for (Item item : itemRepository.findAllByOwner_Id(ownerId)) {
            List<BookingDtoToReturn> bookings = bookingMapper.bookingDtoList(bookingRepository.findAllByItem_id(item.getId()));
            if (!bookings.isEmpty()) {
                if (bookings.size() == 1){
                    items.add(itemMapper.itemToItemDtoWithBooking(item, bookings.getFirst(), null,
                            commentMapper.toCommentDtoList(commentRepository.findAllByItem_Id(item.getId()))));
                }
                items.add(itemMapper.itemToItemDtoWithBooking(item, bookings.get(0), bookings.get(1),
                        commentMapper.toCommentDtoList(commentRepository.findAllByItem_Id(item.getId()))));
            } else {
                items.add(itemMapper.itemToItemDtoWithBooking(item, null, null, null));
            }

        }
        return items;
    }

    @Override
    public List<ItemDto> findItems(String text) {
        log.info("ItemService выполнение запроса на поиск предмета предмета");
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemMapper.itemDtoList(itemRepository.findAllByText(text));
    }

    @Override
    public CommentDto addComment(CommentDto comment, Integer itemId, Integer userId) {
        log.info("ItemService выполнение запроса на добавление предмета");
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("пользователя c id " + userId + "нет в базе"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("предмета c id " + itemId + "нет в базе"));
        validateComment(user, item);
        comment.setCreated(LocalDateTime.now());
        return commentMapper.toCommentDto(commentRepository.save(commentMapper.toComment(comment, user, item)));
    }

    private void isUserOwner(Integer ownerId, ItemDto item) {
        if (!Objects.equals(item.getOwner().getId(), ownerId)) {
            throw new NotFoundException("пользователь не является хозяином предмета");
        }
    }

    private void isUserExists(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("пользователя c id " + userId + "нет в базе");
        }
    }

    private void isItemExists(Integer itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException("предмета c id " + itemId + "нет в базе");
        }
    }

    private void validateComment(User user, Item item) {
        for (Booking booking : bookingRepository.findAllByBooker_Id(user.getId())) {
            if (booking.getItem().equals(item) && booking.getEnd().isBefore(LocalDateTime.now())) {
                return;
            }
        }
        throw new ru.practicum.shareit.exception.ValidationException("пользователь не брал вещь в аренду");
    }

}
