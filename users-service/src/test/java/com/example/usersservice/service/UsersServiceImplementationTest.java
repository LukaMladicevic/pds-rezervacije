package com.example.usersservice.service;

import com.example.usersservice.dto.UserDTO;
import com.example.usersservice.dto.UserSaveDTO;
import com.example.usersservice.entity.User;
import com.example.usersservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplementationTest {

    @Mock UserRepository userRepository;
    @Mock ModelMapper modelMapper;

    @InjectMocks UsersServiceImplemntation usersService;

    @Test
    void getUserByIdReturnDTO() {
        User entity = new User();
        entity.setId(7);
        entity.setFirstName("Luka");
        entity.setLastName("Mladicevic");
        entity.setEmail("lukamladicevic@gmail.com");

        when(userRepository.findById(7)).thenReturn(Optional.of(entity));

        UserDTO dto = new UserDTO();
        dto.setId(7);
        dto.setFirstName("Luka");
        dto.setLastName("Mladicevic");

        when(modelMapper.map(entity, UserDTO.class)).thenReturn(dto);

        UserDTO result = usersService.getUserById(7);

        assertThat(result.getId()).isEqualTo(7);
        assertThat(result.getFirstName()).isEqualTo("Luka");
        assertThat(result.getLastName()).isEqualTo("Mladicevic");
        verify(userRepository).findById(7);
        verify(modelMapper).map(entity, UserDTO.class);
    }

    @Test
    void deleteNonExistingUser() {
        when(userRepository.existsById(777)).thenReturn(false);

        assertThatThrownBy(() -> usersService.deleteUser(777))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).existsById(777);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateEmail() {
        User u = new User();
        u.setId(5);
        u.setEmail("old@gmail.com");

        when(userRepository.findById(5)).thenReturn(Optional.of(u));

        usersService.changeEmail(5, "new@yahoo.com");

        assertThat(u.getEmail()).isEqualTo("new@yahoo.com");
        verify(userRepository).findById(5);
        verify(userRepository).save(u);
    }
}
