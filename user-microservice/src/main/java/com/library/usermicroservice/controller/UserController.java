package com.library.usermicroservice.controller;

import com.library.usermicroservice.model.User;
import com.library.usermicroservice.service.UserDTO;
import com.library.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/user/getAll")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/getUser", method = RequestMethod.GET)
    public ResponseEntity<Optional<User>> getUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        Optional<User> user = userService.getUser(Long.valueOf(id));
        if(!user.isPresent()) throw new ResponseStatusException(NOT_FOUND, "L'utilisateur n'existe pas ou n'a pas été trouvé.");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value="/api/user/myProfil")
    public ResponseEntity<User> getMyProfil(@RequestParam(name = "email", defaultValue = "") String email){
        User user = userService.getMyProfil(email);
        if(user == null) throw new ResponseStatusException(NOT_FOUND, "L'utilisateur n'existe pas ou n'a pas été trouvé.");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/addUser", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.CREATED) ;
    }

    @RequestMapping(value = "/api/user/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDTO userDTO){
       User user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @RequestMapping(value = "/api/user/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        Optional<User> user = userService.getUser(Long.valueOf(id));
        if(!user.isPresent()) throw new ResponseStatusException(NOT_FOUND, "L'utilisateur n'existe pas ou n'a pas été trouvé.");
        userService.deleteUser(user.get().getId());
        return new ResponseEntity<>(user.get().getId(),HttpStatus.OK);
    }
}
