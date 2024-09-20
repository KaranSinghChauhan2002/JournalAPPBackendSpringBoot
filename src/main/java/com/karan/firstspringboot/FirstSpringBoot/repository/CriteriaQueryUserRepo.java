package com.karan.firstspringboot.FirstSpringBoot.repository;

import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CriteriaQueryUserRepo {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Users> admin() {
        Query query = new Query();
        query.addCriteria(Criteria.where("Roles").in("USER")
                .orOperator(Criteria.where("username").is("1")));


        return mongoTemplate.find(query, Users.class);

    }

    public List<Users> EmailEnableForSentimentAnalysis() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true)
                .andOperator(Criteria.where("sentimentAnalysis").is(true)));
        return  mongoTemplate.find(query, Users.class);

    }
}

