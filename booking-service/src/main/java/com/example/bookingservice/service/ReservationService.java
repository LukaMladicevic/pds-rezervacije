package com.example.bookingservice.service;

import com.example.bookingservice.dto.UserDTO;
import com.example.bookingservice.feign.UsersClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UsersClient usersClient;
    private final ReservationIntegrationService integration;

    public UserDTO fetchUser(Integer userID) {
        return integration.fetchUserOrThrow(userID);
    }

    public List<UserDTO> fetchAllUsers(){
        return integration.fetchAllUsersOrThrow();
    }

}
