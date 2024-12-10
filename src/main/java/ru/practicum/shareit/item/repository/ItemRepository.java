package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByOwner_Id(Integer ownerId);

    @Query("select i from Item i " +
            "where i.available = True and ((upper(i.name) like ?1) or (upper(i.description) like ?1))")
    List<Item> findAllByText(String text);
}
