package ru.practicum.request.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.item.dto.ItemForRequest;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RequestMapper {
    public RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getDescription(),
                request.getCreated()
        );
    }

    public Request toRequest(RequestDto request, User author, LocalDateTime created) {
        return new Request(
                request.getId(),
                request.getDescription(),
                author,
                created
        );
    }


    public List<RequestDto> toRequestDtoList(List<Request> requests) {
        List<RequestDto> requestsDto = new ArrayList<>();
        for (Request request : requests) {
            requestsDto.add(new RequestDto(
                    request.getId(),
                    request.getDescription(),
                    request.getCreated())
            );
        }
        return requestsDto;
    }

    public RequestDtoWithItem toRequestDtoWithItem(Request request, List<ItemForRequest> items) {
        return new RequestDtoWithItem(
                request.getId(),
                request.getDescription(),
                request.getCreated(),
                items
        );
    }
}
