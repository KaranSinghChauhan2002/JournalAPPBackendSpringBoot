package com.karan.firstspringboot.FirstSpringBoot.APIResponseObj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Quotes {
    @JsonProperty("quote")
    private String quote;
    @JsonProperty("author")
    private String author;
}
