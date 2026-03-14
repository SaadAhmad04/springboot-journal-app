package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.JournalRepo;
import com.example.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final JournalRepo journalRepo;
    private final UserService userService;

    public JournalService(JournalRepo journalRepo , UserService userService){
        this.journalRepo = journalRepo;
        this.userService = userService;
    }

    public Optional<List<JournalEntity>> getJournalsForUser(){
        UserEntity user = userService.findByUserName(userService.getUsername());
        return Optional.of(user.getJournalEntityList());
    }

    @Transactional
    public Optional<JournalEntity> addJournalForUser(JournalEntity journalEntity){
        LocalDateTime current = LocalDateTime.now();
        journalEntity.setLocalDateTime(current);
        JournalEntity saved = journalRepo.insert(journalEntity);

        UserEntity user = userService.findByUserName(userService.getUsername());
        user.getJournalEntityList().add(saved);
        userService.updateUserJournals(user);
        return Optional.of(journalEntity);
    }

    public Optional<JournalEntity> updateJournal(JournalEntity journalEntity , ObjectId id){
        JournalEntity oldEntry = getJournalById(id).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(journalEntity.getTitle() != null && !journalEntity.getTitle().trim().isEmpty() ? journalEntity.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(journalEntity.getContent() != null && !journalEntity.getContent().trim().isEmpty() ? journalEntity.getContent() : oldEntry.getContent());
            journalRepo.save(oldEntry);
            return Optional.of(oldEntry);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<JournalEntity> getJournalById(ObjectId id){
        UserEntity user = userService.findByUserName(userService.getUsername());
        JournalEntity journalEntity = user.getJournalEntityList().stream().filter(journal -> journal.getId().equals(id)).findFirst().orElse(null);
        if(journalEntity != null){
            return Optional.of(journalEntity);
        }
        return Optional.empty();
    }

    public boolean deleteJournalById(ObjectId id){

        UserEntity user = userService.findByUserName(userService.getUsername());
        JournalEntity journalEntity = user.getJournalEntityList().stream().filter(journal -> journal.getId().equals(id)).findFirst().orElse(null);
        if(journalEntity != null){
            user.getJournalEntityList().remove(journalEntity);
            userService.updateUserJournals(user);
            journalRepo.deleteById(id);
        }

        return journalEntity != null;

    }
}
