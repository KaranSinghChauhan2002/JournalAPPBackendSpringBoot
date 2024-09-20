package com.karan.firstspringboot.FirstSpringBoot.entity;

import com.karan.firstspringboot.FirstSpringBoot.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
public class JournalEntries {
    LocalDate date = LocalDate.now();
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    private String desc;
    private Sentiment sentiment;


}
