package com.karan.firstspringboot.FirstSpringBoot.services;

import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.enums.Sentiment;
import com.karan.firstspringboot.FirstSpringBoot.repository.CriteriaQueryUserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MailSenderForAllService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private CriteriaQueryUserRepo criteriaQueryUserRepo;

    public void NewUserGreetMailSend(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // 'true' indicates that the body is HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Exception occurred in creating user mail sender : ", e);
        }
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void EverySunday() {


        List<Users> userWithEmail = criteriaQueryUserRepo.EmailEnableForSentimentAnalysis();
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            for (int i = 0; i < userWithEmail.size(); i++) {

                Map<Sentiment, Integer> sentiment = new HashMap<>();
                Users user = userWithEmail.get(i);
                List<JournalEntries> journalEntries = user.getEntries();

                for (int j = 0; j < userWithEmail.get(i).getEntries().size(); j++) {

                    if (journalEntries.get(j).getDate().isAfter(LocalDate.now().minus(7, ChronoUnit.DAYS))) {
                        if (sentiment.containsKey(userWithEmail.get(i).getEntries().get(j).getSentiment())) {
                            sentiment.put(userWithEmail.get(i).getEntries().get(j).getSentiment(),
                                    sentiment.get(userWithEmail.get(i).getEntries().get(j).getSentiment()) + 1);
                        } else {
                            sentiment.put(userWithEmail.get(i).getEntries().get(j).getSentiment(), 1);
                        }
                    }

                }
                                int max = 0;
                Sentiment s = null;
                Sentiment samesentimentcount = null;
                for (Map.Entry<Sentiment, Integer> ent : sentiment.entrySet()) {
                    if (ent.getValue() > max) {
                        max = ent.getValue();
                        s = ent.getKey();
                    }
                    if (ent.getValue() == max) {
                        samesentimentcount = ent.getKey();
                    }
                }
                if (max != 0 && s != null && samesentimentcount != null) {
                    simpleMailMessage.setTo(userWithEmail.get(i).getEmail());
                    simpleMailMessage.setSubject("Weekly analysis of your sentiment");
                    simpleMailMessage.setText("Hey this is a weekly analysis of your journals and your last week sentiment is :" +
                            s.toString().toUpperCase());
                    javaMailSender.send(simpleMailMessage);
                    log.info("For user " + userWithEmail.get(i).getUsername() + " and sentiment " + s.toString() + " Mail Sent ! ");
                } else if (max != 0 && s != null) {
                    simpleMailMessage.setTo(userWithEmail.get(i).getEmail());
                    simpleMailMessage.setSubject("Weekly analysis of your sentiment");
                    simpleMailMessage.setText("Hey this is a weekly analysis of your journals and your last week sentiment is :  " +
                            s.toString().toUpperCase());
                    javaMailSender.send(simpleMailMessage);
                    log.info("For user " + userWithEmail.get(i).getUsername() + " and sentiment " + s.toString() + " Mail Sent ! ");

                }
                sentiment.clear();
                log.info("HashMap size is  : " + sentiment.size());

            }


        } catch (Exception e) {
            log.error("Daily Main Error : ", e);
        }
    }
    public void deleteMail(String to){
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText("Thanks for using our app ur all journals are deleted from our servers which may or may-not include any personal details of you");
        simpleMailMessage.setSubject("Deleted why?? :((");
        javaMailSender.send(simpleMailMessage);
    }
}
