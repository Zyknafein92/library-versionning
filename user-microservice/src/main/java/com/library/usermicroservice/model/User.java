package com.library.usermicroservice.model;

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
@Table(name= "user", schema="public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="user_id")
    private Long id;

    @NotNull(message= "Veuillez renseigner le champ: Prénom")
    @Column(name="first_Name")
    private String firstName;

    @NotNull(message= "Veuillez renseigner le champ: Nom")
    @Column(name="last_Name")
    private String lastName;

    @NotNull(message= "Veuillez renseigner le champ: Date de naissance")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "CET", locale = "fr-FR")
    @Column(name="birthday")
    private Date birthday;

    @NotNull(message= "Veuillez renseigner le champ: Numéro de téléphone")
    @Column(name="phone")
    private String phone;

    @NotNull(message= "Veuillez renseigner le champ: Email")
    @Column(name="email", unique= true)
    private String email;

    @NotNull(message= "Veuillez renseigner le champ: Adresse")
    @Column(name="address")
    private String address;

    @NotNull(message= "Veuillez renseigner le champ: Code postal")
    @Column(name="postal_Code")
    private String postalCode;

    @NotNull(message= "Veuillez renseigner le champ: Ville")
    @Column(name="city")
    private String city;

    public User() { }


}