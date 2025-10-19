package com.example.bookingservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name="booking")
@Table(name="bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Accomodation accomodationType;
    @Column(nullable = false)
    private Date startAt;
    @Column(nullable = false)
    private Date endAt;

}
