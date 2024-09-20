package com.karan.firstspringboot.FirstSpringBoot.config;


import com.karan.firstspringboot.FirstSpringBoot.impl.UserServiceImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfig {
    @Autowired
    private UserServiceImpl userService;

    //1t step (add end points security )
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/journal/**","/users/**","/users").authenticated()//secure
                                .requestMatchers("/admin/**").hasRole("ADMIN")//only admin roles can acess it
                                .anyRequest().permitAll()//other not secure
                )
                .httpBasic(Customizer.withDefaults())//security type
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }



    //check password user enter in basic auth after verifying
    // the endpoint will get the username with authentication object
  //Authentication authentication= SecurityContextHolder.getContext().getAuthentication().getName();
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

   }


@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
