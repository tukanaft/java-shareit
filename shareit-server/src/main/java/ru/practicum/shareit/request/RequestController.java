package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoWithItem;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/request")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto addRequest(@RequestHeader("X-Sharer-User-Id") Integer authorId, @RequestBody RequestDto request) {
        log.info("RequestController: выполнение запроса на добавление реквеста");
        return requestService.addRequest(authorId, request);
    }

    @GetMapping
    public List<RequestDtoWithItem> getUserRequests(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("RequestController: выполнение запроса на получение реквестов пользователя: {}", ownerId);
        return requestService.getUserRequests(ownerId);
    }

    @GetMapping("/all")
    public List<RequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("RequestController: выполнение запроса на получение всех реквестов ");
        return requestService.getAllRequests();
    }

    @GetMapping("/{requestId}")
    public RequestDtoWithItem getRequestById(@RequestHeader("X-Sharer-User-Id") Integer ownerId,
                                             @PathVariable("requestId") Integer requestId) {
        log.info("RequestController: выполнение запроса на получение реквестa: {}", requestId);
        return requestService.getRequestById(requestId);
    }
}
