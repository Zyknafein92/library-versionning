package com.library.borrowmicroservice.services;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
public class BorrowDTO {

    private Long id;
    @NotNull(message= "Veuillez renseigner l'ID de l'utilisateur !")
    private String userID;
    @NotNull(message= "Veuillez renseigner l'ID du livre !")
    private String bookID;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private LocalDateTime dateExtend;
    private Boolean isExtend;

}
