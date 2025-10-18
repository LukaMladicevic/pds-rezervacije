package com.example.bookingservice.configuration;


import com.example.bookingservice.dto.ReservationDTO;
import com.example.bookingservice.entity.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(ReservationDTO.class, Reservation.class)
              .addMappings(m -> m.skip(Reservation::setCreatedAt));
        return mapper;
    }

}
