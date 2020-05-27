package com.library.usermicroservice.service;

import com.library.usermicroservice.exceptions.UserNotFoundException;
import com.library.usermicroservice.model.User;
import com.library.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getMyProfil(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UserNotFoundException(" L'utilisateur n'existe pas");
        return user;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userMapper.userDtoToUser(userDTO);
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = userRepository.getOne(userDTO.getId());
        userMapper.updateUserFromUserDTO(userDTO, user);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}