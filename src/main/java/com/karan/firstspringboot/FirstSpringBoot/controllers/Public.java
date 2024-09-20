package com.karan.firstspringboot.FirstSpringBoot.controllers;

import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.services.MailSenderForAllService;
import com.karan.firstspringboot.FirstSpringBoot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class Public {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderForAllService allService;

    //create-User must be public

    @GetMapping
    public Map<String, Object> healthCheck() {
        // Capture start time
        long startTime = System.currentTimeMillis();

        // Perform health check logic (in this case, simply return "OK")
        String status = "OK";

        // Capture end time
        long endTime = System.currentTimeMillis();

        // Calculate latency
        long latency = endTime - startTime;

        // Create response map
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("latency", latency + " ms");

        return response;
    }
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody Users users) {
        String body = "<div style=\"font-family: Arial, sans-serif; color: #333;\">" +
                "<h2 style=\"color: #4CAF50;\">Welcome to your personal journal!</h2>" +
                "<p style=\"font-size: 16px;\">We're excited to help you capture your thoughts, moments, and memories. Start writing, reflecting, and organizing your journey.</p>" +
                "<p style=\"font-style: italic; font-size: 16px; color: #555;\">Your story begins here.</p>" +
                "<p style=\"font-weight: bold; font-size: 18px; color: #4CAF50;\">Happy journaling!</p>" +
                "</div>";
        boolean x=userService.saveUserSecurity(users);
        if (x){
            allService.NewUserGreetMailSend(users.getEmail(),"Welcome to our app "+users.getUsername(), body);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("UserName already exists", HttpStatus.NOT_ACCEPTABLE);
        }




    }
}
