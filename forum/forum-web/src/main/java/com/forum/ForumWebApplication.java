package com.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumWebApplication {
    public static void main(String[] args){
        SpringApplication.run(ForumCommonApplication.class, args);
    }
}
