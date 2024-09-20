package com.karan.firstspringboot.FirstSpringBoot.repository;

import com.karan.firstspringboot.FirstSpringBoot.entity.Appcache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppCacheRepo extends MongoRepository<Appcache, ObjectId> {
}
