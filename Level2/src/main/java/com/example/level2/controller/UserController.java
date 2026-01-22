package com.example.level2.controller;

import com.example.level2.entity.QuoteEntity;
import com.example.level2.entity.UserEntity;
import com.example.level2.services.EmailService;
import com.example.level2.services.QuoteService;
import com.example.level2.services.UserService;
import jakarta.validation.constraints.Email;
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

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private EmailService emailService;



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

    @GetMapping("/quote")
    public ResponseEntity<QuoteEntity> getQuote() {
        QuoteEntity[] quotes = quoteService.getQuotes();
        if (quotes == null || quotes.length == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(quotes[0]);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam @Email String email) {
        emailService.sendEmail(email);
        return ResponseEntity.ok("Email sent successfully..");
    }

}
