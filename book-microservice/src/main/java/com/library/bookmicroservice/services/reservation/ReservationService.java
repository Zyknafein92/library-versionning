package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation getReservation(Long id);

    Reservation getByUserID(Long id);

    List<Reservation> getReservationsByBookID(Long id);

    Reservation createReservation(ReservationDTO reservationDTO);

    void updateReservation(ReservationDTO reservationDTO);

    void deleteReservation(Long id);

}
