package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.dto.ItemForRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemMapper {


    public ru.practicum.shareit.item.dto.ItemDto toItemDto(Item item) {
        return new ru.practicum.shareit.item.dto.ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                null
        );
    }

    public ru.practicum.shareit.item.dto.ItemDto toItemDtoIfRequest(Item item) {
        return new ru.practicum.shareit.item.dto.ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                item.getRequest().getId()
        );
    }

    public Item toItem(ru.practicum.shareit.item.dto.ItemDto item, User owner, Request request) {
        return new Item(
                item.getId(),
                item.getName(),
                item.getDescription(),
                owner,
                item.getAvailable(),
                request
        );
    }

    public List<ru.practicum.shareit.item.dto.ItemDto> itemDtoList(List<Item> items) {
        List<ru.practicum.shareit.item.dto.ItemDto> itemsDto = new ArrayList<>();
        for (Item item : items) {
            itemsDto.add(new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getOwner(),
                    item.getAvailable(),
                    item.getRequest().getId()
            ));
        }
        return itemsDto;
    }

    public ItemDtoWithBooking itemToItemDtoWithBooking(Item item, BookingDtoToReturn lastBooking,
                                                       BookingDtoToReturn nextBooking, List<CommentDto> comments) {
        return new ItemDtoWithBooking(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                item.getRequest(),
                lastBooking,
                nextBooking,
                comments
        );
    }

    public List<ItemForRequest> itemToItemForRequestList(List<Item> items) {
        List<ItemForRequest> itemsForRequest = new ArrayList<>();
        for (Item item : items) {
            itemsForRequest.add(new ItemForRequest(
                    item.getId(),
                    item.getName(),
                    item.getOwner().getId()
            ));
        }
        return itemsForRequest;
    }
}
