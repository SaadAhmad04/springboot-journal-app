package com.example.journalApp.service;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUserName(userName);

        if(user != null){
            String[] roles = user.getRoles().toArray(new String[0]);

            return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(roles)
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + userName);
    }
}