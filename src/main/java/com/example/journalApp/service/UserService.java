package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.JournalRepo;
import com.example.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final JournalRepo journalRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo,
                       JournalRepo journalRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.journalRepo = journalRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<List<UserEntity>> getAllUsers() {
        return Optional.of(new ArrayList<>(userRepo.findAll()));
    }



    public Optional<UserEntity> getUserById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteUserByName() {
        UserEntity user = findByUserName(getUsername());
        List<JournalEntity> journals = user.getJournalEntityList();
        journals.forEach(journalEntity -> journalRepo.deleteById(journalEntity.getId()));
        userRepo.delete(user);
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Optional<UserEntity> updateUserDetails(UserEntity userEntity) {
        String userName = getUsername();
        UserEntity user = findByUserName(userName);
        if (user != null) {
            if (!userEntity.getUserName().isEmpty())user.setUserName(userEntity.getUserName());
            if (!userEntity.getJournalEntityList().isEmpty()) user.setJournalEntityList(userEntity.getJournalEntityList());
            if (!userEntity.getPassword().isEmpty()) user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            UserEntity savedUser = userRepo.save(user);
            return Optional.of(savedUser);
        }
        return Optional.empty();
    }

    public void updateUserJournals(UserEntity userEntity) {
        userRepo.save(userEntity);
    }


    public UserEntity findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }
}

