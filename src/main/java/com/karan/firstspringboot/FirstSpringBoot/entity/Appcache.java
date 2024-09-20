package com.karan.firstspringboot.FirstSpringBoot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appcache")
@Data
@NoArgsConstructor
public class Appcache {
    private String key;
    private String value;
}
