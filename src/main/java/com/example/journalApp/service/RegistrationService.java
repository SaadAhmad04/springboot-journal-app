package com.example.journalApp.service;


import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.JournalRepo;
import com.example.journalApp.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<UserEntity> addUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userRepo.insert(userEntity);
        return Optional.of(userEntity);
    }

    public Optional<UserEntity> addAdmin(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("ADMIN"));
        userRepo.insert(userEntity);
        return Optional.of(userEntity);
    }
}
