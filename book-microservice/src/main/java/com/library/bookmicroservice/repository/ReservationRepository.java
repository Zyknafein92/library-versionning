package com.library.bookmicroservice.repository;

import com.library.bookmicroservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByUserID(Long userID);

    List<Reservation>findAllByBookID(Long bookID);
}
