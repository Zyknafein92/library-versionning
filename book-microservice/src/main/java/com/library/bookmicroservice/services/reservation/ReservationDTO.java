package com.library.bookmicroservice.services.reservation;


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
    String userID;
    String bookID;
}
