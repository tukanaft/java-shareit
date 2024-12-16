package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") Integer authorId, @RequestBody String description) {
        log.info("ItemRequestController выполнение запроса на добавление запроса");
        return requestClient.addRequest(authorId, description);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("RequestController: выполнение запроса на получение реквестов пользователя: {}", ownerId);
        return requestClient.getUserRequests(ownerId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("RequestController: выполнение запроса на получение всех реквестов ");
        return requestClient.getAllRequests(ownerId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                                                 @PathVariable("requestId") Integer requestId) {
        log.info("RequestController: выполнение запроса на получение реквестa: {}", requestId);
        return requestClient.getRequestById(ownerId, requestId);
    }
}
