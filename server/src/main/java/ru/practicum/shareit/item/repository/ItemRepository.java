package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.Request;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByOwner_Id(Integer ownerId);

    List<Item> findAllByRequest_id(Integer requestId);

    @Query("SELECT i FROM Item i " +
            "WHERE i.available = TRUE " +
            "AND (UPPER(i.name) LIKE UPPER(CONCAT('%', ?1, '%')) " +
            "OR UPPER(i.description) LIKE UPPER(CONCAT('%', ?1, '%')))")
    List<Item> findAllByText(String text);

    List<Item> findAllByRequestIdIn(List<Integer> requestIds);
}
