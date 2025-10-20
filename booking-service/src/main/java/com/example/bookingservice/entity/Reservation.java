package com.example.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="reservation")
@Table(name="reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reservation {
    @Id
    @Column(name="booking_id")
    private Integer bookingId; // PK je i FK iz Bookinga
    @OneToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="booking_id")
    @MapsId
    private BookingAccomodation booking;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void makeTime(){
        createdAt = LocalDateTime.now();
    }//odmah pred cuvanje entiteta u bazu cuvamo i parametar kada je kreiran
}
