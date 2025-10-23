package com.example.usersservice;

import com.example.usersservice.controller.UserController;
import com.example.usersservice.dto.UserDTO;
import com.example.usersservice.entity.User;
import com.example.usersservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTestUnit {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
        userController = new UserController(userRepository, modelMapper);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Luka");
        user.setLastName("Mladicevic");

        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setFirstName("Luka");
        dto.setLastName("Mladicevic");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(dto);

        var response = userController.getUserByID(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(dto);

        verify(userRepository).findById(1);
        verify(modelMapper).map(user, UserDTO.class);
    }
}
