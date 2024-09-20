package com.karan.firstspringboot.FirstSpringBoot.APIResponseObj;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExternalAPIUser {
    private String email;
    private String password;
}
