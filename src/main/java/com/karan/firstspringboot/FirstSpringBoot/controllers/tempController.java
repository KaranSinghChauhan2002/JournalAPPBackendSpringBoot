package com.karan.firstspringboot.FirstSpringBoot.controllers;

import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.repository.CriteriaQueryUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/temp")
public class tempController {

    //no @Autowired for criteria query
    @Autowired
    private CriteriaQueryUserRepo queryUserRepo;

    @GetMapping
    public List<Users> getAdmin() {
     if (queryUserRepo.admin()!=null){
         return queryUserRepo.admin();
     }else{
         return null;
     }
    }
}
