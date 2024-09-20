package com.karan.firstspringboot.FirstSpringBoot.repository;

import com.karan.firstspringboot.FirstSpringBoot.entity.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MongoDBRepo extends  MongoRepository<JournalEntries, ObjectId> {

}
