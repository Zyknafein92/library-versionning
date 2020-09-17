package com.library.gateway.services;

import com.library.gateway.exceptions.UserNotFoundException;
import com.library.gateway.model.Role;
import com.library.gateway.model.RoleName;
import com.library.gateway.model.User;
import com.library.gateway.repository.RoleRepository;
import com.library.gateway.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepository repository;

    User user;
    UserDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = User.builder().id((long) 1).email("test@gmail.com").password("test").roles(null).build();
        dto = UserDTO.builder().id((long) 1).email("test@gmail.com").password("test").roles(null).build();
    }

    @Test
    void getUser_GivenID_ReturnUser() {
        when(repository.findById((long)1)).thenReturn(java.util.Optional.ofNullable(user));
        assertThat(service.getUser((long) 1)).isEqualTo(java.util.Optional.ofNullable(user));
    }


    @Test
    void updateUser_GivenNull_ReturnError() {
        when(repository.findById((long)1)).thenReturn(java.util.Optional.empty());
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> service.updateUser(dto));
        assertThat(userNotFoundException.getMessage()).isEqualTo(" L'utilisateur n'existe pas");
    }

    @Test
    void deleteUser_GivenID_ReturnID() {
        when(repository.findById((long)1)).thenReturn(java.util.Optional.ofNullable(user));
        assertThat(service.deleteUser((long) 1)).isEqualTo(1);
    }
}
