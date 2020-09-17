package com.library.bookmicroservice.services.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
public class ReservationDTO {
    private Long id;
    private Date date;
    private Integer reservationPosition;

    @NotNull(message= "Veuillez renseigner un email utilisateur !")
    private String userEmail;

    @NotNull(message= "Veuillez renseigner un id de livre !")
    private String bookID;

    @NotNull(message= "Veuillez renseigner un titre de livre !")
    private String bookTitle;

    private Date bookReturn;
}
