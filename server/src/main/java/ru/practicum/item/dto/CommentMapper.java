package ru.practicum.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.item.model.Comment;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentMapper {
    private final ItemMapper itemMapper;

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                itemMapper.toItemDto(comment.getItem()),
                comment.getCreated()
        );
    }

    public Comment toComment(CommentDto comment, User author, Item item) {
        return new Comment(
                comment.getId(),
                comment.getText(),
                author,
                item,
                comment.getCreated()
        );
    }

    public List<CommentDto> toCommentDtoList(List<Comment> comments) {
        List<CommentDto> commentsDto = new ArrayList<>();
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
