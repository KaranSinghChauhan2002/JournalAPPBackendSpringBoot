package com.karan.firstspringboot.FirstSpringBoot.Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import com.karan.firstspringboot.FirstSpringBoot.repository.userRepoDB;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class ControllerTest {

    @Autowired
    private userRepoDB userRepoDB;


    @Disabled
    @Test
    void userController() {
        assertNull(userRepoDB.findByusername("123"));
    }

    @Test
    void Admin() {
        assertNotNull(userRepoDB.findAll());
    }

    @CsvSource(
            {
                    "Karan202",
                    "ABC",
                    "123",
                    "1"
            }
    )
    @Test
    @Disabled
    void multipleUsers(String username) {
        assertNotNull(userRepoDB.findByusername(username));
    }

    @Test
    void testOBJ(){
        JournalEntries journalEntries1= new JournalEntries();
        journalEntries1.setName("123");
        journalEntries1.setDesc("no desc");
        String s=journalEntries1.toString();
        System.out.println(s.toUpperCase());

    }
}
