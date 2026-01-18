package com.example.level2.controller;

import com.example.level2.entity.UserEntity;
import com.example.level2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    //Delete User
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUserById(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        boolean deleted =  userService.deleteUserByUsername(user);

        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    //Update user
    @PutMapping("/update-user")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        UserEntity user = userService.findByUsername(name);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        user.setPassword(userEntity.getPassword());
        userService.saveNewUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
