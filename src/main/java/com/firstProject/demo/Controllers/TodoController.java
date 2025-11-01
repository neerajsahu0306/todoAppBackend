package com.firstProject.demo.Controllers;


import com.firstProject.demo.Models.TodoModel;
import com.firstProject.demo.Models.UserModel;
import com.firstProject.demo.Services.TodoService;
import com.firstProject.demo.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TodoModel> createTodo(@AuthenticationPrincipal Jwt jwt, @RequestBody TodoModel todoModel) {
        try {
            String clerkId = jwt.getSubject();
            UserModel userModel = userService.getUserByClerkId(clerkId);
            todoService.saveTodo(todoModel);
            userService.addTodoId(userModel.getId(), todoModel.getId());
            return ResponseEntity.ok(todoModel);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoModel>> getAllTodosByUser(@AuthenticationPrincipal Jwt jwt) {
        try {
            String clerkId = jwt.getSubject();
            UserModel userModel = userService.getUserByClerkId(clerkId);
           List<TodoModel> todos = userService.getAllTodosOfUser(userModel.getId());
           if (todos.isEmpty()) {
               return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
           }
           return ResponseEntity.ok(todos);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoModel> updateTodoById(@AuthenticationPrincipal Jwt jwt, @PathVariable ObjectId todoId, @RequestBody Map<String, Object> updates) {
         try {
             String clerkId = jwt.getSubject();
             UserModel userModel = userService.getUserByClerkId(clerkId);
             userService.verifyTodoOwnership(userModel.getId(), todoId);
             TodoModel updatedTodo = todoService.updateById(todoId,updates);
             return ResponseEntity.ok(updatedTodo);
         } catch (NoSuchElementException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
         } catch (Exception e) {
             return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodoById(@AuthenticationPrincipal Jwt jwt, @PathVariable ObjectId todoId) {
        try {
            String clerkId = jwt.getSubject();
            UserModel userModel = userService.getUserByClerkId(clerkId);
            userService.verifyTodoOwnership(userModel.getId(), todoId);
            todoService.deleteById(todoId);
            userService.removeTodoById(userModel.getId(),todoId);
            return ResponseEntity.ok("Todo Deleted Successfully !");
        } catch (EmptyResultDataAccessException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
