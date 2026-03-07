package com.example.journalApp.controller;

import com.example.journalApp.service.JournalService;
import com.example.journalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalService journalService;

    public JournalEntryController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/get-all")
    public List<JournalEntity> getJournals(){
        return journalService.getJournals();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJournal(@RequestBody JournalEntity journalEntity){
        Optional<JournalEntity> response = journalService.addJournal(journalEntity);
        if(response.isPresent()) return new ResponseEntity<>(response.get() , HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("getJournalById/{myId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntity> response = journalService.getJournalById(myId);
        if(!response.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response.get() , HttpStatus.OK);
    }

    @DeleteMapping("deleteById")
    public boolean deleteJournalById(@RequestParam(defaultValue = "") ObjectId id){
        return journalService.deleteJournalById(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id , @RequestBody JournalEntity journalEntity){
        Optional<JournalEntity> response =  journalService.updateJournal(journalEntity , id);
        if(response.isPresent()) return new ResponseEntity<>(response.get() , HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
