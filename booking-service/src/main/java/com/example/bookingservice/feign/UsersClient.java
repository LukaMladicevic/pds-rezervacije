package com.example.bookingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.example.bookingservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="users-service")
public interface UsersClient {

    @GetMapping("/users/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id);

    @GetMapping("/users")
    List<UserDTO> getAllUsers();

}
