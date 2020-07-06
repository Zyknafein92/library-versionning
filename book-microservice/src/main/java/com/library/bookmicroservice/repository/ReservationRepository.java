package com.library.bookmicroservice.repository;

import com.library.bookmicroservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation>findAllByBookID(String bookID);


    List<Reservation> findAllByUserEmailIs(String email);

    @Query("select r from Reservation r where current_date > r.date")
    List<Reservation> findOutDatedReservation();
}
