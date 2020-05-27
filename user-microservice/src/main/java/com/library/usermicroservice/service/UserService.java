package com.library.usermicroservice.service;

import com.library.usermicroservice.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUser(Long id);

    User createUser(UserDTO userDTO);

    User getMyProfil(String pseudo);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long id);
}