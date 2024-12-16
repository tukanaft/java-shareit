package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.item.dto.ItemForRequest;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RequestDtoWithItem {
    private Integer id;
    private String description;
    private LocalDateTime created;
    private List<ItemForRequest> items;
}
