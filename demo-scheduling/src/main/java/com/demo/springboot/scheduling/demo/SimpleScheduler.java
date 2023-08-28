package com.demo.springboot.scheduling.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleScheduler {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // @Scheduled(fixedRate = 2000)
    public void scheduledLookup() {
        System.out.println("The current time is: " + dateFormat.format(new Date()));
    }

    @Scheduled(fixedRateString = "${scheduler.rate}")
    public void scheduledLookupUsingProperties() {
        System.out.println("The current time is: " + dateFormat.format(new Date()));
    }

    // @Scheduled(fixedDelay = 3000)
    // public void scheduledLookupWithDelay() throws InterruptedException {
    //     System.out.println("using interval - the current time is: " + dateFormat.format(new Date()));
    //     Thread.sleep(3000);
    // }
}
