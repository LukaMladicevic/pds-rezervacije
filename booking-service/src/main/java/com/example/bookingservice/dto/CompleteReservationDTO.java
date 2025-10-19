package com.example.bookingservice.dto;

import com.example.bookingservice.entity.Accomodation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompleteReservationDTO {

    private Integer bookingId;
    private String accomodation;
    private Accomodation accomodationType;
    private Date startAt;
    private Date endAt;
    private LocalDateTime createdAt;
    private Integer userId;
    private String firstName;
    private String lastName;

}
