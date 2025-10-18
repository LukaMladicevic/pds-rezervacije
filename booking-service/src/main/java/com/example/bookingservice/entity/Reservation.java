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
@Builder

public class Reservation {
    @Id
    private Integer bookingId; // PK je i foreign key iz Bookinga
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void makeTime(){
        createdAt = LocalDateTime.now();
    }//Odmah pred cuvanje entiteta u bazu cuvamo i parametar kada je kreiran.
}
