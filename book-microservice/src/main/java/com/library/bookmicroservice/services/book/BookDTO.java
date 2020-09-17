package com.library.bookmicroservice.services.book;

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
public class BookDTO {
    private Long id;

    @NotNull(message= "Veuillez renseigner un titre !")
    private String title;

    @NotNull(message= "Veuillez renseigner un auteur !")
    private String author;

    @NotNull(message= "Veuillez renseigner une description !")
    private String description;

    @NotNull(message= "Veuillez renseigner un editeur !")
    private String editor;

    @NotNull(message= "Veuillez renseigner une date de parution !")
    private Date parution;

    @NotNull(message= "Veuillez renseigner un genre !")
    private String gender;

    @NotNull(message= "Veuillez renseigner une image de couverture !")
    private String picture;

    @NotNull(message= "Veuillez renseigner la disponibilité du livre !")
    private Boolean avaible;

    @NotNull(message= "Veuillez renseigner l'id de la bibliothèque !")
    private String libraryID;
}
