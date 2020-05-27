package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Override
    public Reservation getReservation(Long id) {
        return null;
    }

    @Override
    public List<Reservation> getReservations(Long id) {
        return null;
    }

    @Override
    public Reservation createReservation(ReservationDTO reservationDTO) {
        return null;
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {

    }

    @Override
    public void deleteReservation(Long id) {
        
    }
}
