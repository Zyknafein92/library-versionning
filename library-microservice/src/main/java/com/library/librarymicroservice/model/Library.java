package com.library.librarymicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name= "library", schema="public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Library {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="library_id")
    private Long id;

    @NotNull(message= "Veuillez renseigner un nom pour la bibliothèque !")
    @Column(name ="name")
    private String name;

    @NotNull(message= "Veuillez renseigner une adresse !")
    @Column(name ="address")
    private String address;

    @NotNull(message= "Veuillez renseigner un numéro de téléphone !")
    @Column(name ="phone")
    private String phone;

    public Library() {
    }

}
