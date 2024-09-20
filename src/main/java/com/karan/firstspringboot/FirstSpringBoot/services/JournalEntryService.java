package com.karan.firstspringboot.FirstSpringBoot.services;

import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.repository.MongoDBRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;


@Service
public class JournalEntryService {
    @Autowired
    private MongoDBRepo mongoDBRepo;
    @Autowired
    private UserService userService;


    public List<JournalEntries> getAll(@PathVariable String username) {
        Users usersinDB = userService.findByUsername(username);
        if (!usersinDB.getEntries().isEmpty()) {
            return usersinDB.getEntries();
        } else {
            return null;
        }
    }

    @Transactional
    public boolean addEntry(@RequestBody JournalEntries journalEntries,String username) {
        Users UserinDB = userService.findByUsername(username);


        try {
            journalEntries.setDate(LocalDate.from(LocalDateTime.now()));
            mongoDBRepo.save(journalEntries);
            UserinDB.getEntries().add(journalEntries);

            userService.createUser(UserinDB);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }


    }

    public JournalEntries update(@PathVariable ObjectId id, @RequestBody JournalEntries journalEntries, String username) {

        Users user = userService.findByUsername(username);
        boolean getIdtoDelete = false;
        if (user != null) {

            for (int i = 0; i < user.getEntries().size(); i++) {
                if (user.getEntries().get(i).getId().equals(id)) {
                    getIdtoDelete = true;
                    break;
                }
            }

        }
        if (getIdtoDelete) {
            JournalEntries j2 = mongoDBRepo.findById(id).orElse(null);
            if (j2 != journalEntries) {
                j2.setDate(LocalDate.now());
                j2.setName(journalEntries.getName() != null && !Objects.equals(journalEntries.getName(), "") ? journalEntries.getName() : j2.getName());
                j2.setDesc(journalEntries.getDesc() != null && !Objects.equals(journalEntries.getDesc(), "") ? journalEntries.getDesc() : j2.getDesc());


                return mongoDBRepo.save(j2);
            }
        }


        return null;

    }

    @Transactional
    public JournalEntries delete(@RequestParam ObjectId id, String username) {


        Users user = userService.findByUsername(username);
        boolean getIdtoDelete = false;
        if (user != null) {

            for (int i = 0; i < user.getEntries().size(); i++) {
                if (user.getEntries().get(i).getId().equals(id)) {
                    getIdtoDelete = true;
                    break;
                }
            }

        }
        if (getIdtoDelete) {
            JournalEntries delete = mongoDBRepo.findById(id).orElse(null);
            JournalEntries deletedstuff = delete;
            System.out.println(deletedstuff);
            mongoDBRepo.deleteById(id);
            user.getEntries().removeIf(x -> x.getId().equals(id));
            userService.createUser(user);


            return deletedstuff;
        }


        return null;


    }


}
