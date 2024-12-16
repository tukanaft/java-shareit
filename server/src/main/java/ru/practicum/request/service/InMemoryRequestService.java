package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.item.dto.ItemForRequest;
import ru.practicum.item.dto.ItemMapper;
import ru.practicum.item.model.Item;
import ru.practicum.item.repository.ItemRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestDtoWithItem;
import ru.practicum.request.dto.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class InMemoryRequestService implements RequestService {
    final RequestRepository requestRepository;
    final RequestMapper requestMapper;
    final UserRepository userRepository;
    final ItemRepository itemRepository;
    final ItemMapper itemMapper;

    @Override
    public RequestDto addRequest(Integer authorId, RequestDto request) {
        log.info("RequestService: выполнение запроса на добавление реквеста");
        User author = validateUser(authorId);
        return requestMapper.toRequestDto(requestRepository.save(requestMapper.toRequest(request, author, LocalDateTime.now())));
    }

    @Override
    public List<RequestDtoWithItem> getUserRequests(Integer ownerId) {
        log.info("RequestService: выполнение запроса на получение реквестов пользователя: {}", ownerId);
        validateUser(ownerId);
        List<Request> requests = requestRepository.findAllByAuthor_Id(ownerId);
        List<Integer> requestIds = requests.stream().map(Request::getId).toList();
        List<Item> items = itemRepository.findAllByRequestIdIn(requestIds);
        List<RequestDtoWithItem> requestDtoWithItems = new ArrayList<>();
        if (items.isEmpty()) {
            for (Request request : requests) {
                requestDtoWithItems.add(requestMapper.toRequestDtoWithItem(request, null));
            }
        } else {
            Map<Request, List<Item>> requestsWithItems = items.stream().collect(Collectors.groupingBy(item -> item.getRequest()));
            requestsWithItems.forEach((request, itemList) -> {
                List<ItemForRequest> itemsForReq = itemMapper.itemToItemForRequestList(itemList);
                RequestDtoWithItem requestToSend = (requestMapper.toRequestDtoWithItem(request, itemsForReq));
                requestDtoWithItems.add(requestToSend);
            });
        }
        return requestDtoWithItems;
    }

    @Override
    public List<RequestDto> getAllRequests(Integer ownerId) {
        log.info("RequestService: выполнение запроса на получение всех реквестов ");
        return requestMapper.toRequestDtoList(requestRepository.findAllOrderByCreated(userRepository.findById(ownerId).get()));
    }

    @Override
    public RequestDtoWithItem getRequestById(Integer requestId) {
        log.info("RequestService: выполнение запроса на получение реквестa: {}", requestId);
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("реквеста" + requestId + "нет в базе"));
        List<ItemForRequest> items = itemMapper.itemToItemForRequestList(itemRepository.findAllByRequest_id(requestId));
        return requestMapper.toRequestDtoWithItem(request, items);
    }

    private User validateUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("пользователя" + userId + "нет в базе"));
    }
}
