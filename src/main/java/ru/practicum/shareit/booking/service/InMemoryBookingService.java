package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class InMemoryBookingService implements BookingService {
    final BookingRepository bookingRepository;
    final ItemRepository itemRepository;
    final UserRepository userRepository;
    final BookingMapper bookingMapper;

    @Override
    public BookingDtoToReturn addBooking(BookingDto booking, Integer bookerId) {
        log.info("BookingService выполнение запроса на добавление бронирования");
        validateBooking(booking, bookerId);
        booking.setBooker(userRepository.findById(bookerId).get());
        Booking bookingToSave = bookingMapper.toBooking(booking, itemRepository.findById(booking.getItemId()).get());
        bookingToSave.setStatus(Status.WAITING);
        return bookingMapper.toBookingDtoToReturn(bookingRepository.save(bookingToSave));
    }

    @Override
    public BookingDtoToReturn updateStatus(Integer userId, Integer bookingId, Boolean status) {
        log.info("BookingService выполнение запроса на обновление информации о бронировании: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("бронирования c id " + bookingId + "нет в базе"));
        isUserOwner(userId, bookingId);
        if (status) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return bookingMapper.toBookingDtoToReturn(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoToReturn getBooking(Integer bookingId, Integer userId) {
        log.info("BookingService выполнение запроса на отправление информации о бронировании: {}", bookingId);
        BookingDtoToReturn booking = bookingMapper.toBookingDtoToReturn(
                bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("бронирования c id " + bookingId + "нет в базе")));
        validationToGet(booking, userId);
        return booking;
    }

    @Override
    public List<BookingDtoToReturn> getBookingByUser(Integer userId, String state) {
        log.info("BookingService выполнение запроса на отправление информации о бронировании пользователя: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("пользоавтеля с id" + userId + "нет в базе");
        }
        LocalDateTime time = LocalDateTime.now();
        List<BookingDtoToReturn> bookings = switch (state) {
            case "ALL" -> bookingMapper.bookingDtoList(bookingRepository.findAllByBooker_Id(userId));
            case "CURRENT" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByBooker_IdAndEndIsAfterAndStartIsBeforeOrderByStart(userId, time, time));
            case "PAST" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByBooker_IdAndEndIsBeforeOrderByStart(userId, time));
            case "FUTURE" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByBooker_IdAndStartIsAfterOrderByStart(userId, time));
            case "WAITING" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, Status.WAITING));
            case "REJECTED" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, Status.REJECTED));
            default -> new ArrayList<>();
        };
        return bookings;
    }

    @Override
    public List<BookingDtoToReturn> getBookingByOwner(Integer ownerId, String state) {
        log.info("BookingService выполнение запроса на отправление информации о бронировании предметов пользователя: {}", ownerId);
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("пользоавтеля с id" + ownerId + "нет в базе");
        }
        LocalDateTime time = LocalDateTime.now();
        List<BookingDtoToReturn> bookings = switch (state) {
            case "ALL" -> bookingMapper.bookingDtoList(bookingRepository.findAllByItem_Owner_id(ownerId));
            case "CURRENT" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByItem_Owner_idAndEndIsAfterAndStartIsBeforeOrderByStart(ownerId, time, time));
            case "PAST" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByItem_Owner_idAndEndIsAfterOrderByStart(ownerId, time));
            case "FUTURE" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByItem_Owner_idAndStartIsAfterOrderByStart(ownerId, time));
            case "WAITING" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByItem_Owner_idAndStatusOrderByStart(ownerId, Status.WAITING));
            case "REJECTED" ->
                    bookingMapper.bookingDtoList(bookingRepository.findAllByItem_Owner_idAndStatusOrderByStart(ownerId, Status.REJECTED));
            default -> new ArrayList<>();
        };
        return bookings;
    }


    private void validateBooking(BookingDto booking, Integer bookerId) {
        LocalDateTime start = booking.getStart();
        LocalDateTime end = booking.getEnd();
        Item item = itemRepository.findById(booking.getItemId()).orElseThrow(() -> new NotFoundException("предмета" + booking.getItemId() + "нет в базе"));
        if (!userRepository.existsById(bookerId)) {
            throw new NotFoundException("пользователя" + bookerId + "нет в базе");
        }
        if (item.getAvailable().equals(false)) {
            throw new ValidationException("в данный момент предмет не доступен");
        }
        if (start == null || end == null || start.isEqual(end) || start.isBefore(LocalDateTime.now()) || end.isBefore(start)) {
            throw new ValidationException("не введены или не верно введены параметры времени");
        }
    }

    private void isUserOwner(Integer userId, Integer bookingId) {

        if (!Objects.equals(bookingRepository.findById(bookingId).get().getItem().getOwner().getId(), userId)) {
            throw new ValidationException("пользователь" + userId + "не явлеяется хозяином предмта");
        }
    }

    private void validationToGet(BookingDtoToReturn booking, Integer userId) {
        if (Objects.equals(booking.getBooker().getId(), userId) || Objects.equals(booking.getItem().getOwner().getId(), userId)) {
        } else {
            throw new ValidationException("пользователь" + userId + "не явлеяется ни хозяином предмта, ни автором бронирования");
        }
    }
}
