package com.example.bookingservice;

import com.example.bookingservice.controller.ReservationController;
import com.example.bookingservice.dto.UserDTO;
import com.example.bookingservice.entity.Accomodation;
import com.example.bookingservice.entity.BookingAccomodation;
import com.example.bookingservice.entity.Reservation;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.ReservationRepository;
import com.example.bookingservice.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationController.class)
@Import(ReservationControllerWebMvcTest.TestModelMapperConfig.class)
class ReservationControllerWebMvcTest {

    @Autowired MockMvc mockMvc;

    @MockBean ReservationService reservationService;
    @MockBean ReservationRepository reservationRepository;
    @MockBean BookingRepository bookingRepository; // injektuje se u kontroler, iako ga ovaj endpoint ne koristi

    @TestConfiguration
    static class TestModelMapperConfig {
        @Bean ModelMapper modelMapper() {
            return new ModelMapper(); // koristi default; ako imaš svoj MapperConfig, možeš @Import(MapperConfig.class)
        }
    }

    @Test
    void getReservationsByUser_shouldReturn200AndList() throws Exception {
        BookingAccomodation booking = new BookingAccomodation();
        booking.setId(1);
        booking.setLocation("Neka lokacija");
        booking.setAccomodationType(Accomodation.AIRBNB);
        booking.setStartAt(new Date());
        booking.setEndAt(new Date());

        Reservation r = new Reservation();
        r.setBookingId(1);
        r.setBooking(booking);
        r.setUserId(7);
        r.setCreatedAt(LocalDateTime.now());

        when(reservationRepository.findByUserIDFetchBooking(7)).thenReturn(List.of(r));

        UserDTO user = new UserDTO();
        user.setId(7);
        user.setFirstName("Luka");
        user.setLastName("Mladicevic");
        when(reservationService.fetchUser(7)).thenReturn(user);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        mockMvc.perform(get("/reservations/{id}/user", 7))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$[0].userId").value(7))
                .andExpect(jsonPath("$[0].bookingId").value(1))
                .andExpect(jsonPath("$[0].location").value("Neka lokacija"))
                .andExpect(jsonPath("$[0].accomodationType").value("AIRBNB"))
                .andExpect(jsonPath("$[0].firstName").value("Luka"))
                .andExpect(jsonPath("$[0].lastName").value("Mladicevic"));
    }
}
