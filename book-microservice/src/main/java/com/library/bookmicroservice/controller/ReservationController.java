package com.library.bookmicroservice.controller;

import com.library.bookmicroservice.exceptions.ReservationLimitException;
import com.library.bookmicroservice.model.Book;
import com.library.bookmicroservice.model.Reservation;
import com.library.bookmicroservice.services.book.BookDTO;
import com.library.bookmicroservice.services.reservation.ReservationDTO;
import com.library.bookmicroservice.services.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping(value = "/api/reservation/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.getReservation(id);
        if (reservation == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping(value = "/api/reservations/email/{email}")
    public ResponseEntity<List<Reservation>> getAllBooksByUserEmail(@PathVariable("email") String email) {
        List<Reservation> reservations = reservationService.getReservationByUserEmail(email);
        if (reservations == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    @GetMapping(value = "/api/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        if (reservations == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "api/reservations/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByBookID(@PathVariable("id") String id) {
        List<Reservation> reservations = reservationService.getReservationsByBookID(id);
        if (reservations == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping(value = "api/reservation/addReservation")
    public ResponseEntity createReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation cReservation = reservationService.createReservation(reservationDTO);
            if (cReservation == null)
                return ResponseEntity.noContent().build();
            return new ResponseEntity<>(cReservation, HttpStatus.CREATED);
        } catch (ReservationLimitException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping(value = "api/reservation/checkReservation")
    public ResponseEntity<Reservation> checkForBookReservation(@RequestBody BookDTO bookDTO) {
        reservationService.updateBookReservation(String.valueOf(bookDTO.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "api/reservation/updateReservation")
    public ResponseEntity<Void> updateReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationService.updateReservation(reservationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/api/reservation/deleteReservation", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(@RequestParam(name = "id", defaultValue = "") Long id) {
        Reservation reservation = reservationService.getReservation(id);
        if (reservation == null) {
            return ResponseEntity.noContent().build();
        } else {
            reservationService.deleteReservation(reservation.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
