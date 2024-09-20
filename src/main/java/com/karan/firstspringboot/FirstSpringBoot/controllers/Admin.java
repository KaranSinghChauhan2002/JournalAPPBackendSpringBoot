package com.karan.firstspringboot.FirstSpringBoot.controllers;
import com.karan.firstspringboot.FirstSpringBoot.appCache.LoadDataCache;
import com.karan.firstspringboot.FirstSpringBoot.entity.Appcache;
import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.repository.userRepoDB;
import com.karan.firstspringboot.FirstSpringBoot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class Admin {


    @Autowired
    private UserService userService;

    @Autowired
    private LoadDataCache loadDataCache;


    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAll(){
        if (!userService.getAllUsers().isEmpty()){
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("No Users right now",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/restart-init")
    public String  restart(){
        loadDataCache.init();
        return "init Restarted";
    }
}
