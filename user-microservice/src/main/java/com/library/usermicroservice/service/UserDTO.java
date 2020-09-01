package com.library.usermicroservice.service;

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
public class UserDTO {

    private Long id;
    @NotNull(message= "Veuillez renseigner le champ: Prénom")
    private String firstName;
    @NotNull(message= "Veuillez renseigner le champ: Nom")
    private String lastName;
    @NotNull(message= "Veuillez renseigner le champ: Date de naissance")
    private Date birthday;
    @NotNull(message= "Veuillez renseigner le champ: Numéro de téléphone")
    private String phone;
    @NotNull(message= "Veuillez renseigner le champ: Email")
    private String email;
    @NotNull(message= "Veuillez renseigner le champ: Adresse")
    private String address;
    @NotNull(message= "Veuillez renseigner le champ: Code postal")
    private String postalCode;
    @NotNull(message= "Veuillez renseigner le champ: Ville")
    private String city;

}
