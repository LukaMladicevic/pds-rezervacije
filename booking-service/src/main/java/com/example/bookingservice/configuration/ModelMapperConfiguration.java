package com.example.bookingservice.configuration;


import com.example.bookingservice.dto.CompleteReservationDTO;
import com.example.bookingservice.dto.ReservationDTO;
import com.example.bookingservice.entity.BookingLocation;
import com.example.bookingservice.entity.Reservation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);

        mm.typeMap(BookingLocation.class, CompleteReservationDTO.class)
                .addMappings(m -> m.skip(CompleteReservationDTO::setBookingId));

        return mm;
    }
}

