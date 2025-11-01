package com.firstProject.demo.Clerk;


import com.firstProject.demo.Models.UserModel;
import com.firstProject.demo.Repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/clerk")
public class ClerkWebhookController {



    private final UserRepository userRepository;


    public ClerkWebhookController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleUserWebhook( @RequestBody ClerkWebhookEvent event) {

        String userId =  event.getData().getId();
        String firstName = event.getData().getFirst_name();
        String lastName =  event.getData().getLast_name();
        String UserName = (firstName + " " + lastName).trim();
       String emailAddress =  event.getData().getEmail_addresses().get(0).getEmail_address();
       UserModel userModel = new UserModel();
       userModel.setClerkId(userId);
       userModel.setUserName(UserName);
       userModel.setEmail(emailAddress);
       userModel.setPassword("");
       userModel.setTodoId(new ArrayList<>());

       userRepository.save(userModel);
        return ResponseEntity.ok().build();
    }

}
