package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class RequestDto {
    private Integer id;
    private String description;
    private LocalDateTime created;
}
