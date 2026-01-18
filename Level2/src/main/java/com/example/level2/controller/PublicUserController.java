package com.example.level2.controller;

import com.example.level2.entity.UserEntity;
import com.example.level2.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicUserController {
    private static final Logger logger = LoggerFactory.getLogger(PublicUserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/heath-check")
    public String heathCheck(){
        logger.info("API is Working fine");
        return "OK";
    }


    //Create User
    @PostMapping("/save-user")
    public ResponseEntity<UserEntity> saveUser(@Valid @RequestBody UserEntity userEntity){
        try{
            userEntity.setUsername(userEntity.getUsername().toLowerCase());
            UserEntity savedUser = userService.saveNewUser(userEntity);
            logger.info("User created with username {}",userEntity.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            logger.error("Error while saving user", e);
            throw new RuntimeException(e);
        }
    }
}
