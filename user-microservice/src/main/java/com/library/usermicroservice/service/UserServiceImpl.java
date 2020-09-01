package com.library.usermicroservice.service;

import com.library.usermicroservice.exceptions.UserNotFoundException;
import com.library.usermicroservice.model.User;
import com.library.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
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
    public User updateUser(UserDTO userDTO) {
        Optional<User> userOptional = getUser(userDTO.getId());
        User user = null;

        if(userOptional.isPresent()) {
             user = new User (
                    userOptional.get().getId(),
                    userOptional.get().getFirstName(),
                    userOptional.get().getLastName(),
                    userOptional.get().getBirthday(),
                    userOptional.get().getPhone(),
                    userOptional.get().getEmail(),
                    userOptional.get().getAddress(),
                    userOptional.get().getPostalCode(),
                    userOptional.get().getCity()
            );
        }
        if(user == null) throw new UserNotFoundException("L'utilisateur recherché n'existe pas.");

        user = userMapper.updateUserFromUserDTO(userDTO, user);
        userRepository.save(user);
        return user;
    }

    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
