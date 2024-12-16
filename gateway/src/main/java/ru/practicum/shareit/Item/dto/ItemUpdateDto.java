package ru.practicum.shareit.Item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDto {
    String name;
    String description;
    Boolean available;
    Integer requestId;
}
