package com.library.bookmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "book", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @NotNull(message= "Veuillez renseigner un titre !")
    @Column(name = "title")
    private String title;

    @NotNull(message= "Veuillez renseigner un auteur !")
    @Column(name = "author")
    private String author;

    @NotNull(message= "Veuillez renseigner une description !")
    @Size(max = 5000)
    @Column(name = "description")
    private String description;

    @NotNull(message= "Veuillez renseigner un editeur !")
    @Column(name= "editor")
    private String editor;

    @NotNull(message= "Veuillez renseigner une date de parution !")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "CET", locale = "fr-FR")
    private Date parution;

    @NotNull(message= "Veuillez renseigner un genre !")
    @Column(name = "gender")
    private String gender;

    @NotNull(message= "Veuillez renseigner une image de couverture !")
    @Column(name = "picture")
    private String picture;

    @NotNull(message= "Veuillez renseigner la disponibilité du livre !")
    @Column(name = "avaible")
    private Boolean avaible;

    @NotNull(message= "Veuillez renseigner l'id de la bibliothèque !")
    @Column(name = "library_ID")
    private String LibraryID;

    public Book() {
    }

}