package com.karan.firstspringboot.FirstSpringBoot.services;

import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.repository.MongoDBRepo;
import com.karan.firstspringboot.FirstSpringBoot.repository.userRepoDB;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private userRepoDB userRepo;

    @Autowired
    private MongoDBRepo mongoDBRepo;


//with security---------------------------------------
    public List<Users> getAllUsers(){
        if (!userRepo.findAll().isEmpty()){
            return userRepo.findAll();

        }else{
            return null;
        }
    }

    //save user whern we are saving the journal entry to avoid encryption over encryption (hashing)
    public void createUser(Users userinDB) {
        userRepo.save(userinDB);
    }
    public Users findByUsername(String username){

        if (userRepo.findByusername(username)!=null){
            return userRepo.findByusername(username);
        }else{
            return null;
        }
    }

    public Users updateUser(@RequestBody Users users,@PathVariable String username){
        Users userinDB=userRepo.findByusername(username);
        if (userinDB==null){
            return  null;
        }else{
            userinDB.setUsername(!users.getUsername().isEmpty() ?users.getUsername():userinDB.getUsername());
            userinDB.setPassword(!users.getPassword().isEmpty() ?users.getPassword():userinDB.getPassword());
            saveUserSecurity(userinDB);
            return  userinDB;
        }
    }
    @Transactional
    public Users deleteUser(String username){
        Users user=userRepo.findByusername(username);
        List<JournalEntries> je=user.getEntries();
        userRepo.deleteByusername(username);
        for (JournalEntries journalEntries : je) {
            mongoDBRepo.deleteById(journalEntries.getId());
        }
       log.info(user.getUsername()+" "+user.getPassword()+" "+user.getRoles().toString());
        return user;

    }



    //Spring Security//---------------------------------------------------
    private static final BCryptPasswordEncoder  bCryptPasswordEncoder = new BCryptPasswordEncoder();
    //private static final only one for all users //no-load on server

    //1-> Save User wtih hashed password


    //logger for logging

    private  static  final Logger logger=  LoggerFactory.getLogger(UserService.class);
    public boolean saveUserSecurity(@RequestBody Users users){
        try{
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            userRepo.save(users);
            return true;
        }catch (Exception e){
            logger.error("Username Already exists ",users.getUsername(),e);
            log.error("Error for {} :",users.getUsername(),e.getMessage());
            return false;
        }

    }


}
