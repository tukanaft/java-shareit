package ru.practicum.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.booking.model.Booking;
import ru.practicum.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByBooker_Id(Integer userId);

    List<Booking> findAllByBooker_IdAndEndIsAfterAndStartIsBeforeOrderByStart(Integer bookerId, LocalDateTime time, LocalDateTime time2);

    List<Booking> findAllByBooker_IdAndEndIsBeforeOrderByStart(Integer bookerId, LocalDateTime time);

    List<Booking> findAllByBooker_IdAndStartIsAfterOrderByStart(Integer bookerId, LocalDateTime time);

    List<Booking> findAllByBooker_IdAndStatusOrderByStart(Integer userId, Status status);

    List<Booking> findAllByItem_Owner_id(Integer ownerId);

    List<Booking> findAllByItem_Owner_idAndEndIsAfterAndStartIsBeforeOrderByStart(Integer ownerId, LocalDateTime time, LocalDateTime time2);

    List<Booking> findAllByItem_Owner_idAndEndIsAfterOrderByStart(Integer ownerId, LocalDateTime time);

    List<Booking> findAllByItem_Owner_idAndStartIsAfterOrderByStart(Integer ownerId, LocalDateTime time);

    List<Booking> findAllByItem_Owner_idAndStatusOrderByStart(Integer ownerId, Status status);

    List<Booking> findAllByItem_id(Integer itemId);
}
