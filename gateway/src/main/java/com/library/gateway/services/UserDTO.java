package com.library.gateway.services;

import com.library.gateway.model.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    @NotNull(message= "Veuillez renseigner un email !")
    private String email;
    @NotNull(message= "Veuillez renseigner un password !")
    private String password;
    private Set<Role> roles;
}
