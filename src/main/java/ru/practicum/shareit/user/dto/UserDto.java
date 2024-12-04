package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    Integer id;
    String name;
    String email;
}
