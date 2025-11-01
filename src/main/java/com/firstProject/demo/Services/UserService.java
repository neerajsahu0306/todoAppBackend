package com.firstProject.demo.Services;

import com.firstProject.demo.Models.TodoModel;
import com.firstProject.demo.Models.UserModel;
import com.firstProject.demo.Repository.TodoRepository;
import com.firstProject.demo.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {


    private  final UserRepository userRepository;
    private  final TodoRepository todoRepository;

    public UserService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public UserModel getUserByClerkId(String clerkId) {
        return userRepository.findByClerkId(clerkId).orElseThrow(() -> new NoSuchElementException("user not found"));
    }


    public List<TodoModel> getAllTodosOfUser(ObjectId userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found !"));
        List<ObjectId> todoIds = userModel.getTodoId();
        if (todoIds == null || todoIds.isEmpty()) {
            return Collections.emptyList();
        }
        return todoRepository.findAllById(todoIds);
    }


    public void addTodoId(ObjectId userId, ObjectId todoId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found !"));
        userModel.getTodoId().add(todoId);
        userRepository.save(userModel);
    }

    public void verifyTodoOwnership(ObjectId userId, ObjectId todoId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found !"));

        if( !userModel.getTodoId().contains(todoId)) {
            throw  new IllegalArgumentException(" todo doesn't belong to you !");
        }
    }

    public void removeTodoById(ObjectId userId, ObjectId todoId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found !"));
        userModel.getTodoId().remove(todoId);
        userRepository.save(userModel);
    }


}
