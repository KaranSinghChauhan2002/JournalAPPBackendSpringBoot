package com.karan.firstspringboot.FirstSpringBoot.Tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import com.karan.firstspringboot.FirstSpringBoot.enums.Sentiment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class RedisTests {
    @Autowired
    public RedisTemplate redisTemplate;

    JournalEntries journalEntries = new JournalEntries();

    @Test
    void returnvalue() throws JsonProcessingException {

        System.out.println(redisTemplate.opsForValue().get("karan"));
        redisTemplate.opsForValue().get("karan").toString();
        Assertions.assertEquals(redisTemplate.opsForValue().get("karan"), "karan");
        journalEntries.setName("Karan12");
        journalEntries.setDesc("Desc");
        journalEntries.setSentiment(Sentiment.ANGRY);
        journalEntries.setDate(LocalDate.now());


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonn= objectMapper.writeValueAsString(journalEntries);
        redisTemplate.opsForValue().set("obj",jsonn);
        String g= Objects.requireNonNull(redisTemplate.opsForValue().get("obj")).toString();
        JournalEntries j2 = objectMapper.readValues(g, JournalEntries.class);
    }
}
