package com.library.librarymicroservice.services;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@Builder
public class LibraryDTO {

    private Long id;
    @NotNull(message= "Veuillez renseigner un nom pour la bibliothèque !")
    private String name;
    @NotNull(message= "Veuillez renseigner une adresse !")
    private String address;
    @NotNull(message= "Veuillez renseigner un numéro de téléphone !")
    private String phone;

}
