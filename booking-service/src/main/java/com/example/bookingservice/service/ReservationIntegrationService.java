package com.example.bookingservice.service;

import com.example.bookingservice.dto.UserDTO;
import com.example.bookingservice.feign.UsersClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationIntegrationService {

    private final UsersClient usersClient;

    @Retry(name = "externalRetry")
    @CircuitBreaker(name = "externalCb", fallbackMethod = "getUserFallback")
    public UserDTO fetchUserOrThrow(Integer userId) {
        return usersClient.getUserById(userId).getBody(); // Feign poziv
    }

    @Retry(name="externalRetry")
    @CircuitBreaker(name="externalCb",fallbackMethod = "getReservationsFallback")
    public List<UserDTO> fetchAllUsersOrThrow(){
        return usersClient.getAllUsers();
    }

    private UserDTO getReservationsFallback(Throwable t) {
        log.warn("CB fallback za rezervacije cause =" +t.toString());
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Users service trenutno nije dostupan (CB fallback)."
        );
    }

    private UserDTO getUserFallback(Integer userId, Throwable t) {
        log.warn("CB fallback za userId="+userId+" cause = "+t.toString());
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Users service trenutno nije dostupan (CB fallback)."
        );
    }
}

