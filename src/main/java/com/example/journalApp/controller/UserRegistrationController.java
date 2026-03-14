package com.example.journalApp.controller;


import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.RegistrationService;
import com.example.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/register")
public class UserRegistrationController {

    private final RegistrationService registrationService;

    public UserRegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity){
        Optional<UserEntity> response = registrationService.addUser(userEntity);
        return response.map(user -> new ResponseEntity<>(user , HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> addAdmin(@RequestBody UserEntity userEntity){
        Optional<UserEntity> response = registrationService.addAdmin(userEntity);
        return response.map(user -> new ResponseEntity<>(user , HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
