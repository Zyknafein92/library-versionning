package com.library.usermicroservice.service;

import com.library.usermicroservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUser(Long id);

    User createUser(UserDTO userDTO);

    User getMyProfil(String pseudo);

    User updateUser(UserDTO userDTO);

    Long deleteUser(Long id);
}
