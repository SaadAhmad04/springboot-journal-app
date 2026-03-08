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

    public Optional<List<JournalEntity>> getJournalsForUser(String userName){
        UserEntity user = userService.findByUserName(userName);
        return Optional.of(user.getJournalEntityList());
    }

    @Transactional
    public Optional<JournalEntity> addJournalForUser(JournalEntity journalEntity , String userName){
        LocalDateTime current = LocalDateTime.now();
        journalEntity.setLocalDateTime(current);
        JournalEntity saved = journalRepo.insert(journalEntity);

        UserEntity user = userService.findByUserName(userName);
        user.getJournalEntityList().add(saved);
        userService.updateUser(user , userName);
        return Optional.of(journalEntity);
    }

    public Optional<JournalEntity> updateJournal(JournalEntity journalEntity , ObjectId id , String userName){
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
        return journalRepo.findById(id);
    }
    
    public void deleteJournalById(ObjectId id , String userName){
        UserEntity user = userService.findByUserName(userName);
        user.getJournalEntityList().removeIf(journal -> journal.getId().equals(id));
        userService.updateUser(user , userName);
        journalRepo.deleteById(id);
    }
}
