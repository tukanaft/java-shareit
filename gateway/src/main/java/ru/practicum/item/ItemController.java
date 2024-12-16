package ru.practicum.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemUpdateDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                                          @RequestBody @Valid ItemDto item) {
        log.info("Creating item {}, ownerId={}", item, ownerId);
        return itemClient.addItem(ownerId, item);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @RequestBody @Valid ItemUpdateDto item,
                                             @PathVariable Integer itemId) {
        log.info("Updating item {}, ownerId={}", item, ownerId);
        return itemClient.updateItem(ownerId, item, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("ItemController выполнение запроса на отправление всех предметов");
        return itemClient.getItems(ownerId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                                          @PathVariable("itemId") Integer itemId) {
        log.info("Get item {}, ownerId={}", itemId, ownerId);
        return itemClient.getItem(ownerId, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findItems(@RequestHeader("X-Sharer-User-Id") Integer ownerId, @RequestParam String text) {
        log.info("Searching for item");
        return itemClient.findItems(ownerId, text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody @Valid CommentDto comment,
                                             @PathVariable("itemId") Integer itemId) {
        log.info("Creating comment {}, userId={}", comment, userId);
        return itemClient.addComment(comment, itemId, userId);
    }
}
