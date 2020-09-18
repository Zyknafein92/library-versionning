package com.library.bookmicroservice.controller;


import com.library.bookmicroservice.exceptions.ReservationLimitException;

import com.library.bookmicroservice.model.Reservation;
import com.library.bookmicroservice.services.book.BookDTO;
import com.library.bookmicroservice.services.reservation.ReservationDTO;
import com.library.bookmicroservice.services.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    ReservationService reservationService;


    @GetMapping(value = "/api/reservation/{id}")
    public ResponseEntity<Optional<Reservation>> getReservation(@PathVariable("id") Long id) {
        Optional<Reservation> reservation = reservationService.getReservation(id);
        if(!reservation.isPresent()) throw new ResponseStatusException(NOT_FOUND, "La réservation n'existe pas.");
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
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservationToCreate = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(reservationToCreate, HttpStatus.CREATED);

    }

    @PutMapping(value = "api/reservation/checkReservation")
    public ResponseEntity<Reservation> checkForBookReservation(@RequestBody BookDTO bookDTO) {
        reservationService.updateBookReservation(String.valueOf(bookDTO.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "api/reservation/updateReservation")
    public ResponseEntity<Reservation> updateReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
       Reservation reservation = reservationService.updateReservation(reservationDTO);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/reservation/deleteReservation", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteReservation(@RequestParam(name = "id", defaultValue = "") Long id) {
        Optional<Reservation> reservation = reservationService.getReservation(Long.valueOf(id));
        if (!reservation.isPresent()) throw new ResponseStatusException(NOT_FOUND, "La réservation n'existe pas.");
        reservationService.deleteReservation(reservation.get().getId());
        return new ResponseEntity<>(reservation.get().getId(), HttpStatus.OK);
        }
    }

