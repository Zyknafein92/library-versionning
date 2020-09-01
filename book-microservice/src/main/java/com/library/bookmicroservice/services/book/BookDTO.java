package com.library.bookmicroservice.services.book;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String description;
    private String editor;
    private Date parution;
    private String gender;
    private String picture;
    private Boolean avaible;
    private String libraryID;
}
