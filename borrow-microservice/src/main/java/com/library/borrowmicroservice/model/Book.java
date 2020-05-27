package com.library.borrowmicroservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class Book {

    private Long id;
    private String title;
    private String author;
    private String description;
    private Date parution;
    private String gender;
    private String picture;
    private Boolean avaible;
    private String libraryID;

}