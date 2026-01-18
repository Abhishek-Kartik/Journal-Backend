package com.example.level2.services;

import com.example.level2.entity.JournalEntity;
import com.example.level2.entity.UserEntity;
import com.example.level2.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalServices {

    @Autowired
    private  JournalRepo journalRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntity saveEntry(JournalEntity journalEntity, String username){
        try{
            UserEntity user= userService.findByUsername(username);
            JournalEntity saved = journalRepo.save(journalEntity);
            user.getJournalEntity().add(saved);
            userService.saveUser(user);
            return saved;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public JournalEntity saveEntry(JournalEntity journalEntity){
        return journalRepo.save(journalEntity);
    }

    public List<JournalEntity> getAllJournals(){
        return journalRepo.findAll();
    }

    public Optional<JournalEntity> getJournalById(ObjectId id){
        return journalRepo.findById(id);
    }

    @Transactional
    public boolean deleteJournalById(ObjectId id, String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            boolean removed = user.getJournalEntity().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser((user));
                journalRepo.deleteById(id);
                return true;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to delete journal", e);
        }
        return false;
    }
}
