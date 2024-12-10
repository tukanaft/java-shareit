package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @RequestBody ItemDto item) {
        log.info("ItemController выполнение запроса на добавление предмета");
        return itemService.addItem(ownerId, item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @RequestBody ItemDto item,
                              @PathVariable Integer itemId) {
        log.info("ItemController выполнение запроса на обновление предмета: {}", itemId);
        return itemService.updateItem(ownerId, item, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWithBooking getItem(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @PathVariable Integer itemId) {
        log.info("ItemController выполнение запроса на отправление предмета: {}", itemId);
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDtoWithBooking> getItems(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("ItemController выполнение запроса на отправление всех предметов");
        return itemService.getItems(ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> findItems(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @RequestParam String text) {
        log.info("ItemController выполнение запроса на поиск предмета");
        return itemService.findItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody CommentDto comment,
                                 @PathVariable Integer itemId) {
        log.info("ItemController выполнение запроса на добавление каомментарию к предмету: {}", itemId);
        return itemService.addComment(comment, itemId, userId);
    }
}
