package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final JournalRepo journalRepo;

    public JournalService(JournalRepo journalRepo){
        this.journalRepo = journalRepo;
    }

    public List<JournalEntity> getJournals(){
        return new ArrayList<>(journalRepo.findAll());
    }

    public Optional<JournalEntity> addJournal(JournalEntity journalEntity){
        journalRepo.insert(journalEntity);
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
        return journalRepo.findById(id);
    }
    
    public boolean deleteJournalById(ObjectId id){
        journalRepo.deleteById(id);
        return true;
    }
}
