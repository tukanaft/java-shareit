package ru.practicum.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    @NotEmpty
    String name;
    @NotEmpty
    String description;
    @NotNull
    Boolean available;

    Integer requestId;
}
