package com.example.level2.controller;


import com.example.level2.entity.JournalEntity;
import com.example.level2.entity.UserEntity;
import com.example.level2.services.JournalServices;
import com.example.level2.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalServices journalServices;

    @Autowired
    private UserService userService;

    @GetMapping("/journal-list")
    public ResponseEntity<List<JournalEntity>> getJournalsOfUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> journalEntityList = user.getJournalEntity();
        if(journalEntityList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(journalEntityList);
    }

    @GetMapping("/journal-id/{id}")
    public ResponseEntity<JournalEntity> getJournal(@PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> journalCollect =user.getJournalEntity().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!journalCollect.isEmpty()){
            Optional<JournalEntity> journalEntry = journalServices.getJournalById(id);
            if(journalEntry.isPresent()) {
                return ResponseEntity.ok(journalEntry.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save-journal")
    public ResponseEntity<?> saveEntries(@RequestBody JournalEntity myEntries){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            myEntries.setDate(LocalDate.now());
            JournalEntity savedEntry = journalServices.saveEntry(myEntries, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save journal entry");
        }
    }


    @DeleteMapping("/delete-journal/{id}")
    public ResponseEntity<String> deleteJournal( @PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean deleted =  journalServices.deleteJournalById(id, username);

        if (deleted) {
            return ResponseEntity.ok("Journal deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal not found");
        }
    }

    @PutMapping("/update-journal/{id}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userService.findByUsername(username);
        List<JournalEntity> journalCollect =user.getJournalEntity().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!journalCollect.isEmpty()){
            Optional<JournalEntity> optionalOldEntry = journalServices.getJournalById(id);
            JournalEntity oldEntry = optionalOldEntry.get();
            oldEntry.setTitle(
                    newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.getTitle()
            );
            oldEntry.setContent(
                    newEntry.getContent() != null ? newEntry.getContent() : oldEntry.getContent()
            );

            JournalEntity updatedEntry = journalServices.saveEntry(oldEntry);
            return ResponseEntity.ok(updatedEntry);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }
}
