package com.karan.firstspringboot.FirstSpringBoot.impl;

import com.karan.firstspringboot.FirstSpringBoot.entity.Users;
import com.karan.firstspringboot.FirstSpringBoot.repository.userRepoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;



//This impli class simply use to verify the username
//It connect with secrutycofing class--->AuthenticationManagerBuilder

//It checks wether the AuthenticationManagerBuilder authenticated the username or not
//inside that it have a Userdetailservice(impli)

//it helps to find username outsourcing it to other class
//Impli return a User Object which ave password usernmae role etc

//once the user object is created and authenticeated
//api will take the user object.username() and perform actions
@Component
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private userRepoDB repoDB;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repoDB.findByusername(username);
        if (users != null) {
            return User
                    .builder().username(users.getUsername())
                    .password(users.getPassword())
                    .roles(users.getRoles()
                    .toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("UserName Not Found"+username);
    }
}
