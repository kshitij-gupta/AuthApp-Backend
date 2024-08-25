package com.authapp.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authapp.model.User;
import com.authapp.repository.UserRepository;
 
@RestController
@RequestMapping("/api")
class AuthController {
 
    @Autowired
    UserRepository userRepository;
 
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            if(user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
                return new ResponseEntity<>("Invalid Request body: User was not created.", HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);
            return new ResponseEntity<>("User was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody User requestUser) {
        try {
            if(requestUser.getEmail() == null || requestUser.getPassword() == null) {
                return new ResponseEntity<>("Invalid Request body", HttpStatus.BAD_REQUEST);
            }
            User responseUser = userRepository.retrieve(requestUser);
            if(responseUser == null) {
                return new ResponseEntity<>("Invalid Email or Password", HttpStatus.UNAUTHORIZED);
            }
            responseUser.setPassword("[REDACTED]");
            return new ResponseEntity<Object>(responseUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}