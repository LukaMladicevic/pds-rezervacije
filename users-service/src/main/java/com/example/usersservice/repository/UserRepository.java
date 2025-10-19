package com.example.usersservice.repository;

import com.example.usersservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u.firstName from user u")
    List<String> getAllUserNames();

    Boolean existsByEmail(String email);
}
