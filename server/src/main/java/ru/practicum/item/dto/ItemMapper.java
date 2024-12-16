package ru.practicum.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.booking.dto.BookingDtoToReturn;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                null
        );
    }

    public ItemDto toItemDtoIfRequest(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.getAvailable(),
                item.getRequest().getId()
        );
    }

    public Item toItem(ItemDto item, User owner, Request request) {
        return new Item(
                item.getId(),
                item.getName(),
                item.getDescription(),
                owner,
                item.getAvailable(),
                request
        );
    }

    public List<ItemDto> itemDtoList(List<Item> items) {
        List<ItemDto> itemsDto = new ArrayList<>();
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
