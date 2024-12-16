package test.java.ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoToReturn;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.service.BookingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    Integer bookingId = 1;

    BookingDto bookingDto = new BookingDto(
            null,
            LocalDateTime.now(),
            LocalDateTime.now().minusSeconds(1),
            1,
            null,
            null
    );

    BookingDtoToReturn bookingMock = new BookingDtoToReturn(
            1,
            LocalDateTime.now(),
            LocalDateTime.now().minusSeconds(1),
            null,
            null,
            Status.APPROVED
    );
    Integer bookerId = 1;
    List<BookingDtoToReturn> bookingsMock = new ArrayList<>();

    @Test
    void addBooking() throws Exception {
        Mockito.when(bookingService.addBooking(bookingDto, 1)).thenReturn(bookingMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .header("X-Sharer-User-Id", bookerId)
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(bookingMock));
    }

    @Test
    void updateBookingStatus() throws Exception {
        Mockito.when(bookingService.updateStatus(bookerId, bookingId, true)).thenReturn(bookingMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/1")
                        .header("X-Sharer-User-Id", bookerId)
                        .param("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(bookingMock));
    }

    @Test
    void getBooking() throws Exception {
        Mockito.when(bookingService.getBooking(bookingId, bookerId)).thenReturn(bookingMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/bookings/1")
                        .header("X-Sharer-User-Id", bookerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(bookingMock));
    }

    @Test
    void getBookingByUser() throws Exception {
        bookingsMock.add(bookingMock);
        Mockito.when(bookingService.getBookingByUser(bookerId, "ALL")).thenReturn(bookingsMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/bookings")
                        .header("X-Sharer-User-Id", bookerId)
                        .param("status", "ALL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(bookingsMock));
    }

    @Test
    void getBookingByOwner() throws Exception {
        bookingsMock.add(bookingMock);
        Mockito.when(bookingService.getBookingByOwner(bookerId, "ALL")).thenReturn(bookingsMock);
        String actual = mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner")
                        .header("X-Sharer-User-Id", bookerId)
                        .param("status", "ALL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(actual).isEqualTo(objectMapper.writeValueAsString(bookingsMock));
    }
}