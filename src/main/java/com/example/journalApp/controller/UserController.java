package com.example.journalApp.controller;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id){
        Optional<UserEntity> response = userService.getUserById(id);
        return response.map(user -> new ResponseEntity<>(user , HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity){
        Optional<UserEntity> response = userService.updateUserDetails(userEntity);
        return response.map(user -> new ResponseEntity<>(user , HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUser(){
        userService.deleteUserByName();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
