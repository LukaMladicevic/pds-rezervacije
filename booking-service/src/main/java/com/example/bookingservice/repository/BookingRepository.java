package com.example.bookingservice.repository;

import com.example.bookingservice.entity.BookingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingLocation,Integer> {

    @Query("select b.location from booking b")
    List<String> getAllLocations();



}
