package ru.practicum.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemUpdateDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(Integer ownerId, ItemDto item) {
        return post("", ownerId, item);
    }

    public ResponseEntity<Object> updateItem(Integer ownerId, ItemUpdateDto item, Integer itemId) {
        return patch("/" + itemId, ownerId, item);
    }

    public ResponseEntity<Object> getItem(Integer ownerId, Integer itemId) {
        return get("/" + itemId, ownerId);
    }

    public ResponseEntity<Object> findItems(Integer ownerId, String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("?text={text}", ownerId, parameters);
    }

    public ResponseEntity<Object> addComment(CommentDto comment, Integer itemId, Integer userId) {
        return post("/" + itemId + "/comment", userId, null, comment);
    }


    public ResponseEntity<Object> getItems(Integer ownerId) {
        return get("", ownerId);
    }


}
