package com.example.bookingservice.repository;

import com.example.bookingservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUserId(Integer userID);

    @Query("select r from reservation r join fetch r.booking where r.userId = :userID")
    List<Reservation> findByUserIDFetchBooking(@Param("userID") Integer userId);

    @Query("select r from reservation r join fetch r.booking")
    List<Reservation> findAllFetchBooking();

    boolean existsByUserIdAndBookingId(Integer userId, Integer bookingId);
}