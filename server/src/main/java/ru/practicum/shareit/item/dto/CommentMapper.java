package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentMapper {
    private final ItemMapper itemMapper;

    public ru.practicum.shareit.item.dto.CommentDto toCommentDto(Comment comment) {
        return new ru.practicum.shareit.item.dto.CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                itemMapper.toItemDto(comment.getItem()),
                comment.getCreated()
        );
    }

    public Comment toComment(ru.practicum.shareit.item.dto.CommentDto comment, User author, Item item) {
        return new Comment(
                comment.getId(),
                comment.getText(),
                author,
                item,
                comment.getCreated()
        );
    }

    public List<ru.practicum.shareit.item.dto.CommentDto> toCommentDtoList(List<Comment> comments) {
        List<ru.practicum.shareit.item.dto.CommentDto> commentsDto = new ArrayList<>();
        for (Comment comment : comments) {
            commentsDto.add(new CommentDto(
                    comment.getId(),
                    comment.getText(),
                    comment.getAuthor().getName(),
                    itemMapper.toItemDto(comment.getItem()),
                    comment.getCreated())
            );
        }
        return commentsDto;
    }
}
