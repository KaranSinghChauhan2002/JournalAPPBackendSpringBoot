package com.karan.firstspringboot.FirstSpringBoot.controllers;

import com.karan.firstspringboot.FirstSpringBoot.APIResponseObj.ExternalAPIUser;
import com.karan.firstspringboot.FirstSpringBoot.services.ExternalAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExternalAPIs {
    @Autowired
    private ExternalAPIService externalAPIService;

    @GetMapping("/{name}")
    public ResponseEntity<String> quote(@PathVariable String name) {

        if (externalAPIService.retData() != null) {
            return new ResponseEntity<>("Hi " + name + " " + "Quote of the day is " + externalAPIService.retData().getQuote() + " " + "By " + externalAPIService.retData().getAuthor(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping
    public ResponseEntity<ExternalAPIUser> createuserinexternalApi(@RequestBody ExternalAPIUser e){
        ExternalAPIUser user= externalAPIService.SendUser(e);

        if (user!=null){
            return  new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        else{
            return  new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
