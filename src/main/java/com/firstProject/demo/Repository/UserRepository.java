package com.firstProject.demo.Repository;

import com.firstProject.demo.Models.UserModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface UserRepository extends MongoRepository<UserModel, ObjectId> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByClerkId(String clerkId);
}
