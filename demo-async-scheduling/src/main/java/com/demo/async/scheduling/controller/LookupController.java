package com.demo.async.scheduling.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.async.scheduling.model.User;
import com.demo.async.scheduling.service.LookupService;

@Component
public class LookupController {
    
    private static final Logger logger = LoggerFactory.getLogger(LookupController.class);

    @Autowired
    private LookupService lookupService;

    private int userIndex = 0;
    private static final List<String> users = new ArrayList<String>();

    static {
        users.add("arpitparasana");
        users.add("spring-boot");
        users.add("spring-security");
        users.add("Pytorch");
    }
    
    @Scheduled(fixedRate = 4000)
    public void lookup() throws InterruptedException, ExecutionException {
        CompletableFuture<User>  userInfo = lookupService.findUser(users.get(userIndex));
        userIndex = (userIndex + 1) % users.size();
        logger.info("--> " + userInfo.get());
    }
}
