package com.library.gateway.services;

import com.library.gateway.model.User;

public interface UserService {
    User getUser(Long id);

    User createUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long id);
}
