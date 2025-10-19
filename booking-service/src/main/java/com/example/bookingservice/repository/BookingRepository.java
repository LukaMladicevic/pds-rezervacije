package com.example.bookingservice.repository;

import com.example.bookingservice.entity.BookingAccomodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingAccomodation,Integer> {

    @Query("select b.accomodation from booking b")
    List<String> getAllAccomodations();



}
