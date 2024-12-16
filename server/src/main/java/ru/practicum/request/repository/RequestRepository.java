package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByAuthor_Id(Integer authorId);

    @Query("select r from Request r " +
            "where r.author <> ?1 " +
            "order by r.created")
    List<Request> findAllOrderByCreated(User user);
}
