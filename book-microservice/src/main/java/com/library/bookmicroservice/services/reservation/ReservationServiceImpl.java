package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.model.Reservation;
import com.library.bookmicroservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
   private  ReservationRepository reservationRepository;

    @Autowired
   private ReservationMapper reservationMapper;

    @Override
    public Reservation getReservation(Long id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public Reservation getByUserID(Long userID) {
        return reservationRepository.findByUserID(userID);
    }

    @Override
    public List<Reservation> getReservationsByBookID(Long bookID) {
        return reservationRepository.findAllByBookID(bookID);
    }

    @Override
    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.reservatioDtoToReservation(reservationDTO);
        return reservationRepository.save(reservation);
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
      Reservation reservation = reservationRepository.getOne(reservationDTO.getId());
      if(reservation == null) {
          throw new RuntimeException("La réservation de ce livre n'existe pas ou elle est introuvable");
      } else {
          reservationMapper.updateReservationFromReservationDTO(reservationDTO, reservation);
          reservationRepository.save(reservation);
      }
    }

    @Override
    public void deleteReservation(Long id) { reservationRepository.deleteById(id);}
}
