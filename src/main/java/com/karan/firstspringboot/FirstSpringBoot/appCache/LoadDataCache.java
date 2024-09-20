package com.karan.firstspringboot.FirstSpringBoot.appCache;

import com.karan.firstspringboot.FirstSpringBoot.entity.Appcache;
import com.karan.firstspringboot.FirstSpringBoot.repository.AppCacheRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoadDataCache {
    @Autowired
    private AppCacheRepo appCacheRepo;

    public Map<String,String> appCache;


    @PostConstruct
    public void  init(){
        appCache= new HashMap<>();
       List<Appcache> appcacheList= appCacheRepo.findAll();
        for (Appcache appcache : appcacheList) {
            appCache.put(appcache.getKey(), appcache.getValue());
        }
    }

}
