package com.example.journalApp.controller;


import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("all-users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        Optional<List<UserEntity>> response = userService.getAllUsers();
        return response.map(users ->
                new ResponseEntity<>(users , HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
