package com.example.bookingservice.controller;

import com.example.bookingservice.dto.BookingLocationDTO;
import com.example.bookingservice.dto.CompleteReservationDTO;
import com.example.bookingservice.dto.ReservationDTO;
import com.example.bookingservice.dto.UserDTO;
import com.example.bookingservice.entity.BookingLocation;
import com.example.bookingservice.entity.Reservation;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.ReservationRepository;
import com.example.bookingservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;
    private final ReservationRepository reservationRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public boolean checkIfReservationExists(Integer bookingID){
        return reservationRepository.existsById(bookingID);
    }

    public boolean checkIfBookingExists(Integer bookingID){
        return bookingRepository.existsById(bookingID);
    }

    public boolean checkIfUserIsAlreadyBooked(Integer userID,Integer bookingID){
        return reservationRepository.existsByUserIdAndBookingId(userID,bookingID);
    }


    @PostMapping("/reserve")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationDTO reservationDTO){
        Integer bookingID = reservationDTO.getBookingId();
        Integer userID = reservationDTO.getUserId();

        if(checkIfReservationExists(bookingID))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Rezervacija vec postoji.");
        if(!checkIfBookingExists(bookingID))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking za dati ID ne postoji.");
        if(checkIfUserIsAlreadyBooked(userID,bookingID))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Korisnik je vec rezervisao ovaj booking.");

        UserDTO dto = service.fetchUser(userID).orElse(null);
        if(dto != null){
            Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
            reservationRepository.save(reservation);
            return ResponseEntity.status(HttpStatus.OK).body("Uspesno dodata rezervacija.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Neispravan unos.");
    }

    @GetMapping
    public ResponseEntity<?> getAllReseravations(){
        List<Reservation> reservationList = reservationRepository.findAll();
        List<CompleteReservationDTO> completeReservations = new ArrayList<>();
        for(Reservation r : reservationList){
            Integer bookingID = r.getBookingId();
            Integer userID = r.getUserId();
            BookingLocation booking = bookingRepository.findById(bookingID).orElse(null);
            UserDTO userDTO = service.fetchUser(userID).orElse(null);
            if(booking == null || userDTO == null){
                continue;
            }
            CompleteReservationDTO reservationDTO = new CompleteReservationDTO();
            modelMapper.map(userDTO,reservationDTO);
            modelMapper.map(booking,reservationDTO);
            completeReservations.add(reservationDTO);

        }
        return ResponseEntity.status(HttpStatus.OK).body(completeReservations);
    }

}
