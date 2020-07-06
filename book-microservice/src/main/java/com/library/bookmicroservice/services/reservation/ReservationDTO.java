package com.library.bookmicroservice.services.reservation;


import com.library.bookmicroservice.model.Book;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Data
@Getter
@Setter
public class ReservationDTO {
    Long id;
    Date date;
    Integer reservationPosition;
    String userEmail;
    String bookID;
    String bookTitle;
    Date bookReturn;
}
