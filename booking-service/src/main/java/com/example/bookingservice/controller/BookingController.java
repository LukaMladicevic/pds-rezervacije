package com.example.bookingservice.controller;

import com.example.bookingservice.dto.BookingAccomodationDTO;
import com.example.bookingservice.dto.ChangeBookingDateDTO;
import com.example.bookingservice.entity.BookingAccomodation;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.ReservationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final ReservationRepository reservationRepository;

    @GetMapping
    public List<String> getAllReservationBookingAccomodations(){
        return bookingRepository.getAllAccomodations();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccomodationByID(@PathVariable @Min(1) Integer id){
        BookingAccomodation accomodation = bookingRepository.findById(id).orElse(null);
        if(accomodation == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking sa id-om: " +id+" nije pronadjen.");
        }
        BookingAccomodationDTO response = modelMapper.map(accomodation,BookingAccomodationDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping
    public ResponseEntity<?> saveBooking(@Valid @RequestBody BookingAccomodationDTO dto, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(error->error.getDefaultMessage()).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        if(dto.getStartAt().after(dto.getEndAt()) || dto.getStartAt().equals(dto.getEndAt())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Krajnji datum mora biti posle pocetnog");
        }
        BookingAccomodation booking = modelMapper.map(dto,BookingAccomodation.class);
        bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body("Uspesno kreiran booking");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookingByID(@PathVariable @Min(1) Integer id){
        if(reservationRepository.existsById(id))
            reservationRepository.deleteById(id);

        if(bookingRepository.existsById(id)){
            bookingRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Uspesno obrisana lokacija sa id-jem: "+id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoji lokacija sa id-jem: "+id);

    }

    @PatchMapping("/changeDate/{id}")
    public ResponseEntity<?> changeBooking(@Valid @RequestBody ChangeBookingDateDTO dto, @PathVariable Integer id, BindingResult result){
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(error->error.getDefaultMessage()).toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        BookingAccomodation booking = bookingRepository.findById(id).orElse(null);
        if(dto.getStartAt().after(dto.getEndAt()) || dto.getStartAt().equals(dto.getEndAt())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Krajnji datum mora biti posle pocetnog");
        }
        if(booking==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoji booking za id:"+id);
        }
        Date startAt = dto.getStartAt();
        Date endAt = dto.getEndAt();
        booking.setStartAt(startAt);
        booking.setEndAt(endAt);
        bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.OK).body("Uspesno azuriran booking");
    }


}
