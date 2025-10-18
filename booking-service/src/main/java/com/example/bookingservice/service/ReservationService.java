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
    private final ReservationIntegrationService integration;

    public UserDTO fetchUser(Integer userID) {

        return integration.fetchUserOrThrow(userID);
    }

}
