package com.library.bookmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "date")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET", locale = "fr-FR")
    private Date date;

    @NotNull(message= "Veuillez renseigner un email utilisateur !")
    @Column(name = "user_email")
    @NotNull
    private String userEmail;

    @NotNull(message= "Veuillez renseigner un id de livre !")
    @Column(name = "book_id")
    private String bookID;

    @NotNull(message= "Veuillez renseigner un titre de livre !")
    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_return")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET", locale = "fr-FR")
    private Date bookReturn;

    @Column(name = "user_position")
    private Integer reservationPosition;

}
