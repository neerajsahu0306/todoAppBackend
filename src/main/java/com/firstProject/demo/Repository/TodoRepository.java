package com.firstProject.demo.Repository;

import com.firstProject.demo.Models.TodoModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<TodoModel, ObjectId> {
}
