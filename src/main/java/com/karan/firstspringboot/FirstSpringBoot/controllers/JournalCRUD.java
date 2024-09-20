package com.karan.firstspringboot.FirstSpringBoot.controllers;

import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.services.JournalEntryService;
import com.karan.firstspringboot.FirstSpringBoot.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalCRUD {
    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

       if (journalEntryService.getAll(username)!=null){
           return  new ResponseEntity<>(journalEntryService.getAll(username),HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJournal(@RequestBody JournalEntries journalEntries) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        if (journalEntryService.addEntry(journalEntries,username)) {
            return new ResponseEntity<>("Added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not Added", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody JournalEntries journalEntries) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        if (journalEntryService.update(id, journalEntries,username) != null) {
            return new ResponseEntity<>(journalEntryService.update(id, journalEntries,username), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam ObjectId id) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        if (journalEntryService.delete(id,username) != null) {
            return new ResponseEntity<>(journalEntryService.delete(id,username), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

