package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.JournalRepo;
import com.example.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public Optional<List<UserEntity>> getUsers(){
        return Optional.of(new ArrayList<>(userRepo.findAll()));
    }

    public Optional<UserEntity> addUser(UserEntity userEntity){
        userRepo.insert(userEntity);
        return Optional.of(userEntity);
    }


    public Optional<UserEntity> getUserById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteUserByName(String userName){
        UserEntity user = findByUserName(userName);
        userRepo.delete(user);
    }

    public Optional<UserEntity> updateUser(UserEntity userEntity , String username){
        UserEntity user = findByUserName(username);
        if(user != null){
            user.setUserName(userEntity.getUserName());
            user.setPassword(userEntity.getPassword());
            user.setJournalEntityList(userEntity.getJournalEntityList());
            UserEntity savedUser = userRepo.save(user);
            return Optional.of(savedUser);
        }
        return Optional.empty();
    }

    public UserEntity findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}

