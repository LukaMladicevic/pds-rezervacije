package com.example.usersservice.service;

import com.example.usersservice.dto.UserDTO;
import com.example.usersservice.dto.UserSaveDTO;

import java.util.List;

public interface UsersService {
    UserDTO getUserById(Integer id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserSaveDTO dto);
    void deleteUser(Integer id);
    void changeEmail(Integer id, String newEmail);
}