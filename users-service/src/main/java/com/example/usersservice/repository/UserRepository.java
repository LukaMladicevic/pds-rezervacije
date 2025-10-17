package com.example.usersservice.repository;

import com.example.usersservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u.firstName from user u")
    List<String> getAllUserNames();

    //@Query("select u from user u where u.id= :id")
    //Optional<User> getUserByID(@Param("id") int id);

//    @Query("update user u set u.email = :email where u.id = :id")
//    Optional<User> updateUserEmailByID(@Param("id") int id,@Param("email") String email);

    Boolean existsByEmail(String email);
}
