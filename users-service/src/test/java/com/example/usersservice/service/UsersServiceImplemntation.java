package com.example.usersservice.service;

import com.example.usersservice.dto.UserDTO;
import com.example.usersservice.dto.UserSaveDTO;
import com.example.usersservice.entity.User;
import com.example.usersservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImplemntation implements UsersService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO getUserById(Integer id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return modelMapper.map(u, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO createUser(UserSaveDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email already in use: " + dto.getEmail());
        }
        User entity = modelMapper.map(dto, User.class);
        User saved = userRepository.save(entity);
        return modelMapper.map(saved, UserDTO.class);
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void changeEmail(Integer id, String newEmail) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        u.setEmail(newEmail);
        userRepository.save(u);
    }
}
