package com.library.bookmicroservice.repository;

import com.library.bookmicroservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByBookID(String bookid);

    List<Reservation>findAllByBookID(String bookID);

    @Query("select r from Reservation r where current_date > r.date")
    List<Reservation> findOutDatedReservation();
}
