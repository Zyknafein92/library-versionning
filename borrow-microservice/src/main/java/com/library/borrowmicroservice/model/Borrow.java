package com.library.borrowmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name= "borrow", schema="public")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Borrow {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="borrow_id")
    private Long id;

    @NotNull(message= "Veuillez renseigner l'ID de l'utilisateur !")
    @Column(name="user_id")
    private String userID;

    @NotNull(message= "Veuillez renseigner l'ID du livre !")
    @Column(name="book_id", unique = true)
    private String bookID;

    @Column(name="date_start")
    @NotNull(message= "Veuillez renseigner une date d'emprunt !")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET", locale = "fr-FR")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name="date_end")
    @NotNull(message= "Veuillez renseigner une date de retour !")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET", locale = "fr-FR")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    @Column(name="date_extend")
    @NotNull(message= "Veuillez renseigner une date d'extention d'emprunt' !")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "CET", locale = "fr-FR")
    @Temporal(TemporalType.DATE)
    private Date dateExtend;

    @NotNull(message= "Veuillez renseigner le champ d'extention d'emprunt !")
    @Column(name="is_extend")
    private Boolean isExtend;

}
