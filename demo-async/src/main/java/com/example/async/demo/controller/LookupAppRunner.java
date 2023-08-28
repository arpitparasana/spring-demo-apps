package com.example.async.demo.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.async.demo.model.User;
import com.example.async.demo.service.LookupService;

@Component
public class LookupAppRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LookupAppRunner.class);

    @Autowired
    private LookupService lookupService;

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<User> info1 = lookupService.findUser("arpitparasana");
        CompletableFuture<User> info2 = lookupService.findUser("spring-boot");
        
        CompletableFuture.allOf(info1, info2).join();

        logger.info("--> " + info1.get());
        logger.info("--> " + info2.get());
    }
}
