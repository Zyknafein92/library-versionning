package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation getReservation(Long id);

    List<Reservation> getReservations();

    List<Reservation> getReservationsByBookID(String id);

    List<Reservation> getReservationByUserEmail(String email);

    Reservation createReservation(ReservationDTO reservationDTO);

    void updateReservation(ReservationDTO reservationDTO);

    void deleteReservation(Long id);

    void updateBookReservation(String bookID);

}
