package com.clever.days.scheduler;

import com.clever.days.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InvalidationScheduler {

//    @Autowired
//    private UserService service;

    @Scheduled(cron = "* 0 0 * * ?")
    public void invalidateCache() {
        System.out.println("Сработал шедулер");
    }
}
