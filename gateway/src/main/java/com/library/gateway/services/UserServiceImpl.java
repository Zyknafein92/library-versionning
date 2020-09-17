package com.library.gateway.services;


import com.library.gateway.exceptions.UserNotFoundException;
import com.library.gateway.model.Role;
import com.library.gateway.model.RoleName;
import com.library.gateway.model.User;
import com.library.gateway.repository.RoleRepository;
import com.library.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Optional<User> getUser(Long id) {
       return userRepository.findById(id);
    }

    @Override
    public User createUser(UserDTO userDTO) {

        if( userDTO.getRoles() == null ) {
            Set<Role> roles = new HashSet<>();
            Role roleUser = roleRepository.findByName(RoleName.ROLE_USER);
            roles.add(roleUser);
            userDTO.setRoles(roles);
        }

        User user = userMapper.userDtoToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User user = null;
        Optional<User> optionalUser = getUser(userDTO.getId());

        if(optionalUser.isPresent()) {
            user = new User (
                    optionalUser.get().getId(),
                    optionalUser.get().getEmail(),
                    optionalUser.get().getPassword(),
                    optionalUser.get().getRoles());
        }
        else throw new UserNotFoundException(" L'utilisateur n'existe pas");

        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userMapper.updateUserFromUserDTO(userDTO, user);
        userRepository.save(user);
        return user;
    }

    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
