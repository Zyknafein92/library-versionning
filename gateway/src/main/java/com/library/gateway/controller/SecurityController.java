package com.library.gateway.controller;

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
    public ResponseEntity<User> getUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        User user = userService.getUser(Long.valueOf(id));
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PostMapping(value="/api/security/login")
    public JwtResponse login (@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    }


    @RequestMapping(value = "/api/security/addUser", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        if(user == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(user, HttpStatus.CREATED) ;
    }

    @RequestMapping(value = "/api/security/updateUser", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/api/security/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "id", defaultValue = "")  String id) {
        User user = userService.getUser(Long.valueOf(id));
        if(user == null) return ResponseEntity.noContent().build();
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
