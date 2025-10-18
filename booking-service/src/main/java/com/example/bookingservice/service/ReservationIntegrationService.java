package com.example.bookingservice.service;

import com.example.bookingservice.dto.UserDTO;
import com.example.bookingservice.feign.UsersClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationIntegrationService {

    private final UsersClient usersClient;

    @Retry(name = "usersServiceRetry")
    @CircuitBreaker(name = "usersServiceCb", fallbackMethod = "getUserFallback")
    public UserDTO fetchUserOrThrow(Integer userId) {
        return usersClient.getUserById(userId).getBody(); // Feign poziv
    }

    private UserDTO getUserFallback(Integer userId, Throwable t) {
        log.warn("CB fallback za userId={}, cause={}", userId, t.toString());
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Users service trenutno nije dostupan (CB fallback)."
        );
    }
}

