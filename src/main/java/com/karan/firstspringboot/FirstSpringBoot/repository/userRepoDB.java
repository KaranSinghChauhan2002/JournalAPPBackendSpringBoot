package com.karan.firstspringboot.FirstSpringBoot.repository;

import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepoDB extends MongoRepository<Users, ObjectId> {
    Users findByusername(String username);
    Users deleteByusername(String username);

}
