package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoWithItem;

import java.util.List;

@Service
public interface RequestService {
    RequestDto addRequest(Integer authorId, RequestDto request);

    List<RequestDtoWithItem> getUserRequests(Integer ownerId);

    List<RequestDto> getAllRequests(Integer ownerId);

    RequestDtoWithItem getRequestById(Integer requestId);
}
