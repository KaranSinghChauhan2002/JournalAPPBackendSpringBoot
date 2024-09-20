package com.karan.firstspringboot.FirstSpringBoot.controllers;

import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.services.UserService;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UsersControllers {
    @Autowired
    private UserService userService;





    @GetMapping()
    public ResponseEntity<Users> findByID() {
        //it have the user object after authentication is sucessful we can retereive the username from it and perform actions
        //NOTE:- every API should use this
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        System.out.println(username);

        if (userService.findByUsername(username) != null) {
            return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Users> updateUsers(@RequestBody Users users) {
        //sending username and password in basic auth
        //and after that its verified we get the object in authentication object
        //passing username we get by authentication
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.toString()+"Successful");
        String username=authentication.getName();
        if (userService.updateUser(users, username) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(userService.updateUser(users, username), HttpStatus.ACCEPTED);
        }
    }
    @DeleteMapping
    public  ResponseEntity<Users> deleteUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.toString()+"Successful");
        if (userService.findByUsername(authentication.getName())!=null){
            return new ResponseEntity<>(userService.deleteUser(authentication.getName()),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }
}
