package com.library.gateway.controller;

import com.library.gateway.exceptions.UserNotFoundException;
import com.library.gateway.jwt.JwtProvider;
import com.library.gateway.jwt.JwtResponse;
import com.library.gateway.model.User;
import com.library.gateway.services.UserDTO;
import com.library.gateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/api/security/getUser", method = RequestMethod.GET)
    public ResponseEntity<Optional<User>> getUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        Optional<User> user = userService.getUser(Long.valueOf(id));
        if(!user.isPresent()) throw new UserNotFoundException("L'utilisateur n'existe pas");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value="/api/security/login")
    public JwtResponse login (@Valid @RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        } catch (Exception e) {
            throw new ResponseStatusException (UNAUTHORIZED, " Identification incorrecte");
        }
    }


    @RequestMapping(value = "/api/security/addUser", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.CREATED) ;
    }

    @RequestMapping(value = "/api/security/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDTO userDTO){
       User user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @RequestMapping(value = "/api/security/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        Optional<User> user = userService.getUser(Long.valueOf(id));
        if(!user.isPresent()) throw new ResponseStatusException(NOT_FOUND, "L'utilisateur n'existe pas.");
        userService.deleteUser(user.get().getId());
        return new ResponseEntity<>(user.get().getId(),HttpStatus.OK);
    }
}
