package com.karan.firstspringboot.FirstSpringBoot.Tests;

import com.karan.firstspringboot.FirstSpringBoot.services.MailSenderForAllService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EverySundayTest {
    @Autowired
    private MailSenderForAllService mailSenderForAllService;
    @Test
    void sendingmail(){
        mailSenderForAllService.EverySunday();
    }
}
