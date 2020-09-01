package com.library.usermicroservice.service;

import com.library.usermicroservice.exceptions.UserNotFoundException;
import com.library.usermicroservice.model.User;
import com.library.usermicroservice.repository.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserMapperImpl mapper = new UserMapperImpl();

    User user1;

    User user2;

    UserDTO userDTO;

    List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user1 = User.builder().id((long) 1).firstName("test1").lastName("test1").birthday(new Date()).phone("0000000000").email("test@gmail.com").address("test").postalCode("92300").city("Levallois").build();
        user2 = User.builder().id((long) 1).firstName("test2").lastName("test2").birthday(new Date()).phone("0000000000").email("test2@gmail.com").address("test2").postalCode("92300").city("Levallois").build();
        userList.add(user1);
        userList.add(user2);

        userDTO = UserDTO.builder().id((long) 1).firstName("test1").lastName("test1").birthday(new Date()).phone("0000000000").email("test@gmail.com").address("test").postalCode("92300").city("Levallois").build();
    }



    @Test
    void testGetUsers_GivenListUsers_ReturnList() {
        when(userRepository.findAll()).thenReturn(userList);

        assertEquals(2, service.getUsers().size());
    }

    @Test
    void testGetUsers_GivenNull_ReturnNull() {
        when(userRepository.findAll()).thenReturn(null);

        assertNull(service.getUsers());
    }

    @Test
    void testGetMyProfil_GivenEmail_ReturnUser() {
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(user1);

        assertThat(service.getMyProfil(user1.getEmail())).isEqualTo(user1);
    }

    @Test
    void testGetMyProfil_GivenNull_ReturnNull() {
        when(userRepository.findByEmail(null)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> service.getMyProfil(null));
    }

    @Test
    void testGetMyProfil_GivenWrongEmail_ReturnNull() {
        when(userRepository.findByEmail("test")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> service.getMyProfil("test"));
    }

    @Test
    void testCreateUser_GivenAllInformations_ReturnUser() {
        when(userRepository.save(mapper.userDtoToUser(userDTO))).thenReturn(user1);

        assertThat(service.createUser(userDTO)).isEqualTo(user1);
    }

    @Test
    void updateUser() {
        when(userRepository.getOne(userDTO.getId())).thenReturn(user1);
        when(mapper.updateUserFromUserDTO(userDTO,user1)).thenReturn(user1);

        service.updateUser(userDTO);
        assertThat(service.updateUser(userDTO)).isEqualTo(user1);

    }

    @Test
    void deleteUser() {

    }
}
