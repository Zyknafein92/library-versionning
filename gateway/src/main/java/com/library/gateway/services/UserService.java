package com.library.gateway.services;

import com.library.gateway.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long id);

    User createUser(UserDTO userDTO);

    User updateUser(UserDTO userDTO);

    Long deleteUser(Long id);
}
