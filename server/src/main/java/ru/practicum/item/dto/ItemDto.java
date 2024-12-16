package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.user.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    Integer id;
    String name;
    String description;
    User owner;
    Boolean available;
    Integer requestId;
}
