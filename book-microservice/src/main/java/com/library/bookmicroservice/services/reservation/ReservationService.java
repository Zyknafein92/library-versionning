package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> getReservation(Long id);

    List<Reservation> getReservations();

    List<Reservation> getReservationsByBookID(String id);

    List<Reservation> getReservationByUserEmail(String email);

    Reservation createReservation(ReservationDTO reservationDTO);

    Reservation updateReservation(ReservationDTO reservationDTO);

    Long deleteReservation(Long id);

    void updateBookReservation(String bookID);

}
