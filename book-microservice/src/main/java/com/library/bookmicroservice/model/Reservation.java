package com.library.bookmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "reservation", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    Long id;

    @Column(name = "date")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET", locale = "fr-FR")
    Date date;

    @Column(name = "user_email")
    String userEmail;

    String bookID;

    String bookTitle;

}
