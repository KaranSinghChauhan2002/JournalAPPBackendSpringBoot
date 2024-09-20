package com.karan.firstspringboot.FirstSpringBoot.services;

import com.karan.firstspringboot.FirstSpringBoot.APIResponseObj.ExternalAPIUser;
import com.karan.firstspringboot.FirstSpringBoot.APIResponseObj.Quotes;
import com.karan.firstspringboot.FirstSpringBoot.appCache.LoadDataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalAPIService {
    @Value("${quote.get}")
    private String API ;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadDataCache loadDataCache;


    public Quotes retData() {
        ResponseEntity<Quotes> quote = restTemplate.exchange(API, HttpMethod.GET, null, Quotes.class);
        Quotes send = quote.getBody();
        if (send != null) {
            return send;
        } else {
            return null;
        }
    }


    public ExternalAPIUser SendUser(ExternalAPIUser externalAPIUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User", "Karan");
        HttpEntity<ExternalAPIUser> http = new HttpEntity<>(externalAPIUser, httpHeaders);
        ResponseEntity<ExternalAPIUser> externalAPIUserResponseEntity = restTemplate.exchange(loadDataCache.appCache.get("save"),
                HttpMethod.POST, http, ExternalAPIUser.class);
        ExternalAPIUser e = externalAPIUserResponseEntity.getBody();
        if (e != null) {
            return e;
        } else {
            return null;
        }
    }
}
