package com.example.bookingservice.service;

import com.example.bookingservice.dto.UserDTO;
import com.example.bookingservice.entity.BookingLocation;
import com.example.bookingservice.entity.Reservation;
import com.example.bookingservice.feign.UsersClient;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UsersClient usersClient;

    public Optional<UserDTO> fetchUser(Integer userID) {

        ResponseEntity<UserDTO> response = usersClient.getUserById(userID);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return Optional.empty();
        }

        return Optional.of(response.getBody());
    }

}
