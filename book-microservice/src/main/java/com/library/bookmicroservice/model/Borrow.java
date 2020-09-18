package com.library.bookmicroservice.model;


import lombok.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Borrow {
    private Long id;
    private String userID;
    private String bookID;
    private Date dateStart;
    private Date dateEnd;
    private Date dateExtend;
    private Boolean isExtend;
}
