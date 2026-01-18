package com.example.level2.controller;


import com.example.level2.entity.UserEntity;
import com.example.level2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserEntity>> getAllUser(){
        List<UserEntity> users = userService.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/find-username/{name}")
    public ResponseEntity<UserEntity> findByUsername(@PathVariable String name){
        UserEntity user = userService.findByUsername(name);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<UserEntity> createAdmin(@RequestBody UserEntity userEntity){
        userService.createAdminUser(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }
}
