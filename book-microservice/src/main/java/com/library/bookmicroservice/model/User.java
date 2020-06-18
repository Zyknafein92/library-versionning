package com.library.bookmicroservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class User {
    private Long id;
    private String email;
}
