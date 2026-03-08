package com.example.journalApp.controller;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.JournalService;
import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.service.UserService;
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

    @GetMapping("/get-all/{userName}")
    public ResponseEntity<List<JournalEntity>> getJournalsForUser(@PathVariable String userName){
        Optional<List<JournalEntity>> list = journalService.getJournalsForUser(userName);
        return list.map(journalEntities -> new ResponseEntity<>(journalEntities, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add/{userName}")
    public ResponseEntity<?> addJournalForUser(@RequestBody JournalEntity journalEntity , @PathVariable String userName){
        Optional<JournalEntity> response = journalService.addJournalForUser(journalEntity , userName);
        if(response.isPresent()) return new ResponseEntity<>(response.get() , HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("getJournalById/{myId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntity> response = journalService.getJournalById(myId);
        if(!response.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response.get() , HttpStatus.OK);
    }

    @DeleteMapping("deleteById/{userName}")
    public ResponseEntity<?> deleteJournalById(@RequestParam(defaultValue = "") ObjectId id , @PathVariable String userName){
        journalService.deleteJournalById(id , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update/{userName}/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id , @PathVariable String userName , @RequestBody JournalEntity journalEntity){
        Optional<JournalEntity> response =  journalService.updateJournal(journalEntity , id , userName);
        if(response.isPresent()) return new ResponseEntity<>(response.get() , HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
