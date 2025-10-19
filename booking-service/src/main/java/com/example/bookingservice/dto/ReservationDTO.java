package com.example.bookingservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDTO {

    @NotBlank(message="Booking id mora biti unet")
    @Min(1)
    private Integer bookingId;
    @NotBlank(message="User id mora biti unet")
    @Min(1)
    private Integer userId;


}
