package com.library.usermicroservice.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String phone;
    private String email;
    private String address;
    private String postalCode;
    private String city;

}