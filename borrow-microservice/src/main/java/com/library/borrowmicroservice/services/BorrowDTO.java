package com.library.borrowmicroservice.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BorrowDTO {

    private Long id;
    private String userID;
    private String bookID;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private LocalDateTime dateExtend;
    private Boolean isExtend;

}