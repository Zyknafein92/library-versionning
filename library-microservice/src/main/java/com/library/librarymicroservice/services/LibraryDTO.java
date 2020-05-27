package com.library.librarymicroservice.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LibraryDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;

}