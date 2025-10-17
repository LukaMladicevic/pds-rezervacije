package com.example.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeBookingDateDTO {
    @NotNull(message="Pocetak bookinga je obavezan")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startAt;
    @NotNull(message="Kraj bookinga je obavezan")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endAt;
}
