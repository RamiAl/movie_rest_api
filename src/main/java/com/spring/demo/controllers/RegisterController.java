package com.spring.demo.controllers;

import com.spring.demo.configurations.MyUserDetailsService;
import com.spring.demo.db.UserRepository;
import com.spring.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/register")
public class RegisterController {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserRepository repository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        if (repository.findDistinctFirstByUsernameIgnoreCase(user.getUsername()) == null) {
            myUserDetailsService.addUser(user.getUsername(), user.getPassword(), "USER");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "username is already taken");
    }
}
