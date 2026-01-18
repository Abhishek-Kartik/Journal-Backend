package com.example.level2.services;

import com.example.level2.entity.UserEntity;
import com.example.level2.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity saveNewUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of("USER"));
        userRepo.save(userEntity);
        return userEntity;
    }
    public UserEntity saveUser(UserEntity userEntity){
        userRepo.save(userEntity);
        return userEntity;
    }


    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    public Optional<UserEntity> getUserById(ObjectId id){
        return userRepo.findById(id);
    }

    public boolean deleteUserById(ObjectId id) {
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public UserEntity findByUsername(String name) {
        return userRepo.findByUsernameIgnoreCase(name);
    }

    public boolean deleteUserByUsername(String user) {
        if(userRepo.findByUsernameIgnoreCase(user)!=null) {
            userRepo.deleteByUsernameIgnoreCase(user);
            return true;
        }
        return false;
    }

    public void createAdminUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of("USER", "ADMIN"));
        userRepo.save(userEntity);
    }
}
