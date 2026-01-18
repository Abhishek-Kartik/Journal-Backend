package com.example.level2.repository;

import com.example.level2.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsernameIgnoreCase(String name);

    void deleteByUsernameIgnoreCase(String user);
}
