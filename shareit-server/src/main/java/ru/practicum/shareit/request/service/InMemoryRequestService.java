package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemForRequest;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoWithItem;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        log.info("UserService выполнение запроса на добавление пользователя");
        return requestMapper.toRequestDto(requestRepository.save(requestMapper.toRequest(request,
                userRepository.findById(authorId).orElseThrow(() -> new NotFoundException("пользователя" + authorId + "нет в базе")),
                LocalDateTime.now()
        )));
    }

    @Override
    public List<RequestDtoWithItem> getUserRequests(Integer ownerId) {
        log.info("UserService выполнение запроса на добавление пользователя");
        List<RequestDtoWithItem> requests = new ArrayList<>();
        for (Request request : requestRepository.findAllByAuthor_Id(ownerId)) {
            List<ItemForRequest> items = itemMapper.itemToItemForRequestList(itemRepository.findAllByRequest_Author_id(ownerId));
            requests.add(requestMapper.toRequestDtoWithItem(request, items));
        }
        return requests;
    }

    @Override
    public List<RequestDto> getAllRequests() {
        log.info("UserService выполнение запроса на добавление пользователя");
        return requestMapper.toRequestDtoList(requestRepository.findAllOrderByCreated());
    }

    @Override
    public RequestDtoWithItem getRequestById(Integer requestId) {
        log.info("UserService выполнение запроса на добавление пользователя");
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("реквеста" + requestId + "нет в базе"));
        List<ItemForRequest> items = itemMapper.itemToItemForRequestList(itemRepository.findAllByRequest_id(requestId));
        return requestMapper.toRequestDtoWithItem(request, items);
    }


}
