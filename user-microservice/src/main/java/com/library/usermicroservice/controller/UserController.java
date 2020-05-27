package com.library.usermicroservice.controller;

import com.library.usermicroservice.model.User;
import com.library.usermicroservice.service.UserDTO;
import com.library.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<User> getUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        User user = userService.getUser(Long.valueOf(id));
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/api/user/myProfil")
    public ResponseEntity<User> getMyProfil(@RequestParam(name = "email", defaultValue = "") String email){
        User user = userService.getMyProfil(email);
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/api/user/addUser", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.CREATED) ;
    }

    @RequestMapping(value = "/api/user/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/api/user/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        User user = userService.getUser(Long.valueOf(id));
        if(user == null) return ResponseEntity.noContent().build();
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}