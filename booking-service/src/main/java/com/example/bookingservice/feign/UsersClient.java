package com.example.bookingservice.feign;

import com.example.bookingservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="users-service")
public interface UsersClient {

    @GetMapping("/users/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id);

}
